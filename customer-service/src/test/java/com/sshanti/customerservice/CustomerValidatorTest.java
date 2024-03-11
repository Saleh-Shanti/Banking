package com.sshanti.customerservice;

import com.sshanti.bank.exceptions.requests.Messages;
import com.sshanti.bank.model.dto.CustomerDto;
import com.sshanti.bank.model.enums.CustomerGender;
import com.sshanti.bank.model.enums.CustomerType;
import com.sshanti.customerservice.control.CustomerValidator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomerValidatorTest {

    @InjectMocks
    private CustomerValidator customerValidator;

    public CustomerValidatorTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testValidCustomer() {

        CustomerDto customer = new CustomerDto();
        customer.setName("John Doe");
        customer.setCustomerType(CustomerType.RETAIL.name());
        customer.setAge(25);
        customer.setGender(CustomerGender.MALE.name());


        List<String> errorMessages = customerValidator.isValidCustomer(customer);


        assertTrue(errorMessages.isEmpty(), "No errors should be present for a valid customer");
    }

    @Test
    void testInvalidName() {

        CustomerDto customer = new CustomerDto();
        customer.setAge(50);
        customer.setAddress("Add");
        customer.setGender(CustomerGender.FEMALE.name());
        customer.setCustomerType(CustomerType.RETAIL.name());

        List<String> errorMessages = customerValidator.isValidCustomer(customer);

        assertEquals(1, errorMessages.size(), "Should have one error message for an empty name");
        assertEquals(Messages.EMPTY_NAME_MESSAGE.getMessage(), errorMessages.get(0));
    }

    @Test
    void testInvalidType() {

        CustomerDto customer = new CustomerDto();
        customer.setName("John Doe");
        customer.setCustomerType("InvalidType");
        customer.setAge(50);
        customer.setAddress("Add");
        customer.setGender(CustomerGender.FEMALE.name());


        List<String> errorMessages = customerValidator.isValidCustomer(customer);

        assertEquals(1, errorMessages.size(), "Should have one error message for an invalid customer type");
        assertEquals(Messages.INVALID_CUSTOMER_TYPE.getMessage(), errorMessages.get(0));
    }

    @Test
    void testInvalidAge() {

        CustomerDto customer = new CustomerDto();
        customer.setName("John Doe");
        customer.setCustomerType(CustomerType.CORPORATE.name());
        customer.setAge(10);
        customer.setAddress("Add");
        customer.setGender(CustomerGender.FEMALE.name());


        List<String> errorMessages = customerValidator.isValidCustomer(customer);


        assertEquals(1, errorMessages.size(), "Should have one error message for an invalid age");
        assertEquals(Messages.AGE_ERROR_MESSAGE.getMessage(), errorMessages.get(0));
    }

    @Test
    void testInvalidGender() {

        CustomerDto customer = new CustomerDto();
        customer.setName("John Doe");
        customer.setCustomerType(CustomerType.RETAIL.name());
        customer.setAge(25);
        customer.setGender("InvalidGender");


        List<String> errorMessages = customerValidator.isValidCustomer(customer);


        assertEquals(1, errorMessages.size(), "Should have one error message for an invalid gender");
        assertEquals(Messages.INVALID_CUSTOMER_Gender.getMessage(), errorMessages.get(0));
    }


}

