package com.sshanti.customerservice;

import com.sshanti.bank.exceptions.entity.EntityNotFoundException;
import com.sshanti.bank.exceptions.requests.BadRequestException;
import com.sshanti.bank.exceptions.requests.ServerException;
import com.sshanti.bank.model.Customer;
import com.sshanti.bank.model.dto.CustomerDto;
import com.sshanti.bank.model.enums.CustomerGender;
import com.sshanti.bank.model.enums.CustomerType;
import com.sshanti.bank.service.CustomerService;
import com.sshanti.customerservice.control.APIControl;
import com.sshanti.customerservice.control.CustomerControl;
import com.sshanti.customerservice.control.CustomerValidator;
import com.sshanti.customerservice.util.ZookeeperServiceUrlProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.management.ServiceNotFoundException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControlTest {

    @Mock
    private CustomerValidator customerValidator;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerControl customerControl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetCustomerById() throws EntityNotFoundException {
        Long customerId = 1L;
        Customer customer = new Customer();
        when(customerService.getCustomerById(customerId)).thenReturn(customer);
        customer.setId(1L);
        customer.setAge(20);
        customer.setGender(CustomerGender.MALE);
        customer.setCustomerType(CustomerType.RETAIL);
        CustomerDto result = customerControl.getCustomerById(customerId);

        assertNotNull(result);
        verify(customerService, times(1)).getCustomerById(customerId);
    }

    @Test
    void testCreateCustomer() throws BadRequestException, ServerException, ServiceNotFoundException, EntityNotFoundException {
        // Arrange
        CustomerDto customerDto = new CustomerDto();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setCustomerType(CustomerType.RETAIL);
        customer.setName("Name");
        customer.setAddress("Add");
        customer.setAge(20);
        customer.setGender(CustomerGender.MALE);
        when(customerValidator.isValidCustomer(customerDto)).thenReturn(Collections.emptyList());
        when(customerService.createCustomer(any(Customer.class))).thenReturn(customer);

        CustomerDto result = CustomerDto.toDto(customerService.createCustomer(customer));

        assertNotNull(result);
    }

    @Test
    void testCreateCustomerWithValidationErrors() throws BadRequestException, ServerException, ServiceNotFoundException {
        CustomerDto customerDto = new CustomerDto();
        when(customerValidator.isValidCustomer(customerDto)).thenReturn(Collections.singletonList("Validation error"));

        assertThrows(BadRequestException.class, () -> customerControl.createCustomer(customerDto));
        verify(customerService, never()).createCustomer(any(Customer.class));
    }

    @Test
    void testCreateCustomerWithServiceException() throws BadRequestException, ServerException, ServiceNotFoundException {

        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerId("1");
        customerDto.setCustomerType(CustomerType.RETAIL.name());
        customerDto.setAge(50);
        customerDto.setGender(CustomerGender.FEMALE.name());
        customerDto.setName("Name");


        when(customerValidator.isValidCustomer(customerDto)).thenReturn(Collections.emptyList());
        when(customerService.createCustomer(any(Customer.class))).thenThrow(new RuntimeException("Service exception"));


        assertThrows(ServerException.class, () -> customerControl.createCustomer(customerDto));
        verify(customerService, times(1)).createCustomer(any(Customer.class));
    }


    @Test
    void testDeleteCustomer() throws ServerException {

        Long customerId = 1L;
        doNothing().when(customerService).deleteCustomer(customerId);


        customerControl.deleteCustomer(customerId);


        verify(customerService, times(1)).deleteCustomer(customerId);
    }

    @Test
    void testDeleteCustomerWithException() throws ServerException {

        Long customerId = 1L;
        doThrow(new RuntimeException("Delete exception")).when(customerService).deleteCustomer(customerId);


        assertThrows(ServerException.class, () -> customerControl.deleteCustomer(customerId));
        verify(customerService, times(1)).deleteCustomer(customerId);
    }

}
