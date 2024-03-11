package com.sshanti.bank.service;


import com.sshanti.bank.exceptions.entity.EntityNotFoundException;
import com.sshanti.bank.exceptions.entity.EntityPersistException;
import com.sshanti.bank.model.Customer;
import com.sshanti.bank.repository.CustomerRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static com.sshanti.bank.exceptions.requests.Messages.CUSTOMER_NOT_FOUND;
import static com.sshanti.bank.exceptions.requests.Messages.ERROR_PERSISTING_MESSAGE;

@Service
public class CustomerService {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CustomerService.class);
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Customer createCustomer(Customer customer) {
        try {
            logger.info("Persisting customer {}", customer.getName());
            Customer savedCustomer = customerRepository.save(customer);

            if (savedCustomer == null)
                throw new EntityPersistException(String.format(ERROR_PERSISTING_MESSAGE.getMessage(), customer.toString()));

            logger.info("Customer {} with ID {} has been saved.", customer.getName(), customer.getFormattedId());
            return savedCustomer;
        } catch (Exception e) {
            logger.error("Error while persisting customer due to {}", e.getMessage());
            return null;
        }
    }

    public Customer updateCustomer(Long id, Customer customer) throws EntityNotFoundException {
        Optional<Customer> optionalExistingCustomer = customerRepository.findById(id);

        if (!optionalExistingCustomer.isPresent())
            throw new EntityNotFoundException(String.format(CUSTOMER_NOT_FOUND.getMessage(), id));

        logger.info("Updating customer {} with id {}", customer.getName(), customer.getFormattedId());
        Customer existingCustomer = optionalExistingCustomer.get();
        existingCustomer.setName(customer.getName());
        existingCustomer.setCustomerType(customer.getCustomerType());
        existingCustomer.setAddress(customer.getAddress());
        existingCustomer.setAge(customer.getAge());
        existingCustomer.setGender(customer.getGender());
        return customerRepository.save(existingCustomer);

    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
