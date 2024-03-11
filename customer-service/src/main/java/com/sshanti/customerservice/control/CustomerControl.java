package com.sshanti.customerservice.control;

import com.sshanti.bank.exceptions.entity.EntityNotFoundException;
import com.sshanti.bank.model.Customer;
import com.sshanti.bank.model.dto.CustomerDto;
import com.sshanti.bank.service.CustomerService;
import com.sshanti.bank.exceptions.requests.BadRequestException;
import com.sshanti.bank.exceptions.requests.ServerException;
import com.sshanti.customerservice.util.URLMapping;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import com.sshanti.customerservice.util.ZookeeperServiceUrlProvider;

import javax.management.ServiceNotFoundException;

import java.util.Collections;
import java.util.List;


@Component
public class CustomerControl {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CustomerControl.class);
    @Autowired
    CustomerValidator customerValidator;

    @Autowired
    CustomerService customerService;

    @Autowired
    ZookeeperServiceUrlProvider zookeeperServiceUrlProvider;

    @Autowired
    APIControl apiControl;

    public CustomerDto getCustomerById(Long id) throws EntityNotFoundException {
        return CustomerDto.toDto(customerService.getCustomerById(id));
    }

    public CustomerDto createCustomer(CustomerDto customer) throws BadRequestException, ServerException, ServiceNotFoundException {

        logger.info("Validating Customer Data ...");
        List<String> errorMessages = customerValidator.isValidCustomer(customer);
        if (!errorMessages.isEmpty()) {
            logger.error("Error creating customer due to :");
            for (String message : errorMessages) {
                logger.error(message);
            }

            throw new BadRequestException(errorMessages);
        }

        try {

            logger.info("Creating new customer ....");
            Customer savedCustomer = customerService.createCustomer(Customer.fromDto(customer));
            logger.info("Customer has been created successfully.");


            // If there is accounts has been sent with the customer, call account service to validate and persist data.
            if (customer.getAccounts() != null && !customer.getAccounts().isEmpty()) {
                logger.info("Validating accounts...");
                String url = buildURL(savedCustomer.getId());
                ResponseEntity<Object> responseEntity = apiControl.callAPI(url, HttpMethod.POST, customer.getAccounts());

                if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                    logger.error("Couldn't Add Customer, please check account service logs.");
                    throw new ServerException(Collections.singletonList("Couldn't Add Customer, please check account service logs."));
                }
                logger.info("Accounts for customer {} has been added successfully.", savedCustomer.getId());
            }

            return CustomerDto.toDto(savedCustomer);

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServerException(Collections.singletonList(e.getMessage()));
        }

    }

    private String buildURL(Long customerId) throws ServiceNotFoundException {
        StringBuilder sb = new StringBuilder();
        sb.append(zookeeperServiceUrlProvider.serviceUrl(ZookeeperServiceUrlProvider.ACCOUNT_SERVICE))
                .append(String.format(URLMapping.ADD_NEW_ACCOUNTS.getUrl(), customerId));

        return sb.toString();
    }

    public CustomerDto updateCustomer(Long customerId, CustomerDto updatedCustomer) throws EntityNotFoundException, BadRequestException {
        logger.info("Validating Updated Customer Data ...");
        List<String> errorMessages = customerValidator.isValidCustomer(updatedCustomer);
        if (!errorMessages.isEmpty()) {
            logger.error("Error updating customer due to :");
            for (String message : errorMessages) {
                logger.error(message);
            }

            throw new BadRequestException(errorMessages);
        }
        return CustomerDto.toDto(customerService.updateCustomer(customerId, Customer.fromDto(updatedCustomer)));

    }

    public void deleteCustomer(Long customerId) throws ServerException {
        try {
            logger.info("Deleting customer with id {}", customerId);
            Customer customer = customerService.getCustomerById(customerId);
            if (customer == null)
                logger.info("Customer with id {} is not found", customerId);

            customerService.deleteCustomer(customerId);
            logger.info("Customer with id {} , Deleted successfully.", customerId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServerException(Collections.singletonList(e.getMessage()));
        }

    }
}

