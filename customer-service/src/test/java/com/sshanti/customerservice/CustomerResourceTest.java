package com.sshanti.customerservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sshanti.bank.exceptions.entity.EntityNotFoundException;
import com.sshanti.bank.exceptions.requests.BadRequestException;
import com.sshanti.bank.exceptions.requests.ServerException;
import com.sshanti.bank.model.dto.CustomerDto;
import com.sshanti.customerservice.boundary.CustomerResource;
import com.sshanti.customerservice.control.CustomerControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CustomerResourceTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerControl customerControl;

    @InjectMocks
    private CustomerResource customerResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerResource).build();
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void getCustomerById_Success() throws Exception {
        CustomerDto mockCustomerDto = new CustomerDto();
        mockCustomerDto.setCustomerId("1");
        mockCustomerDto.setName("John Doe");

        when(customerControl.getCustomerById(anyLong())).thenReturn(mockCustomerDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void getCustomerById_NotFound() throws Exception {
        when(customerControl.getCustomerById(anyLong())).thenThrow(new EntityNotFoundException("Customer not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/1"))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void createCustomer_Success() throws Exception, ServerException, BadRequestException {
        CustomerDto mockCustomerDto = new CustomerDto();
        mockCustomerDto.setName("Jane Doe");
        when(customerControl.createCustomer(mockCustomerDto))
                .thenReturn(mockCustomerDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockCustomerDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Customer Jane Doe has been added with id null"));
    }

    @WithMockUser(username = "Saleh", password = "Password", roles = "ADD")
    @Test
    void createCustomer_ValidationError() throws Exception, ServerException, BadRequestException {
        CustomerDto invalidCustomerDto = new CustomerDto(); // Invalid data
        List<String> errors = List.of("Name cannot be empty.", "Invalid Customer Type.", "Age cannot be under 18", "Invalid Customer Gender.");
        when(customerControl.createCustomer(invalidCustomerDto))
                .thenThrow(new BadRequestException(errors));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidCustomerDto))
                        .with(user("Saleh").password("Password").roles("ADMIN", "ADD")))
                .andExpect(status().isBadRequest());
    }

}
