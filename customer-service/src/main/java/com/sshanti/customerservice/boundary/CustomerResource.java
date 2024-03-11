package com.sshanti.customerservice.boundary;


import com.sshanti.bank.exceptions.entity.EntityNotFoundException;
import com.sshanti.bank.model.dto.CustomerDto;
import com.sshanti.customerservice.control.CustomerControl;
import com.sshanti.bank.exceptions.requests.BadRequestException;
import com.sshanti.bank.exceptions.requests.ServerException;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.sshanti.bank.exceptions.requests.Messages.*;


@RestController
@RequestMapping("/api/v1/customers")
public class CustomerResource {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CustomerResource.class);

    @Autowired
    private CustomerControl customerControl;

    @GetMapping("/{customerId}")
    @PreAuthorize("hasAnyRole('READ','ADMIN')")
    @ApiOperation(value = "Get a customer by ID", notes = "Retrieve information about a customer by providing their ID.")
    public ResponseEntity<CustomerDto> getCustomerById(@Valid @PathVariable Long customerId) {
        try {
            logger.info("New find by id request...");
            CustomerDto customer = customerControl.getCustomerById(customerId);
            return ResponseEntity.ok(customer);
        } catch (EntityNotFoundException enf) {
            logger.warn(String.format(CUSTOMER_NOT_FOUND.getMessage(), customerId));
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADD', 'ADMIN')")
    @ApiOperation(value = "Adding a new customer", notes = "Creating a new customer.")
    public ResponseEntity<Object> createCustomer(@Valid @RequestBody CustomerDto customer) {

        try {
            logger.info("New Add new customer request.");
            CustomerDto createdCustomer = customerControl.createCustomer(customer);
            logger.info("Customer {} Added successfully.", createdCustomer.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(String.format(CUSTOMER_ADDED_SUCCESSFULLY.getMessage(), createdCustomer.getName(), createdCustomer.getCustomerId()));

        } catch (BadRequestException be) {
            logger.error(INVALID_VALUES_ERROR_MESSAGE.getMessage(), be.getErrorMessage().getErrors());
            return ResponseEntity.badRequest().body(be.getErrorMessage());
        } catch (ServerException se) {
            logger.error(GENERAL_ERROR_MESSAGE.getMessage(), se.getErrorMessage().getErrors());
            return ResponseEntity.internalServerError().body(se.getErrorMessage());
        } catch (Exception e) {
            logger.error(GENERAL_ERROR_MESSAGE.getMessage(), e.getCause().getMessage(), e);
            return ResponseEntity.internalServerError().body(e.getCause().getMessage());
        }
    }

    @PutMapping("/{customerId}")
    @PreAuthorize("hasAnyRole('UPDATE', 'ADMIN')")
    @ApiOperation(value = "Updating a customer by ID and the updated customer.", notes = "Update an existing customer.")
    public ResponseEntity<Object> updateCustomer(
            @PathVariable Long customerId,
            @RequestBody CustomerDto updatedCustomer) {
        try {
            logger.info("New update customer request.");
            CustomerDto updateCustomer = customerControl.updateCustomer(customerId, updatedCustomer);
            logger.info("Customer {} has been updated successfully.", customerId);
            return ResponseEntity.status(HttpStatus.OK).body(String.format(CUSTOMER_UPDATED_SUCCESSFULLY.getMessage(), updateCustomer.getName(), updateCustomer.getCustomerId()));
        } catch (BadRequestException be) {
            logger.error(INVALID_VALUES_ERROR_MESSAGE.getMessage(), be.getErrorMessage().getErrors());
            return ResponseEntity.badRequest().body(be.getErrorMessage());
        } catch (EntityNotFoundException e) {
            logger.warn(String.format(CUSTOMER_NOT_FOUND.getMessage(), customerId));
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error(GENERAL_ERROR_MESSAGE.getMessage(), e.getCause().getMessage(), e);
            return ResponseEntity.internalServerError().body(e.getCause().getMessage());
        }
    }

    @DeleteMapping("/{customerId}")
    @PreAuthorize("hasAnyRole('DELETE', 'ADMIN')")
    @ApiOperation(value = "Delete a customer by ID", notes = "Delete a customer by providing their ID.")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long customerId) {
        try {
            logger.info("New delete customer request.");

            customerControl.deleteCustomer(customerId);

            return ResponseEntity.status(HttpStatus.OK).body(String.format(CUSTOMER_DELETED_SUCCESSFULLY.getMessage(), customerId));
        } catch (ServerException | Exception e) {
            logger.warn(String.format(GENERAL_ERROR_MESSAGE.getMessage(), customerId));
            return ResponseEntity.internalServerError().build();
        }
    }
}
