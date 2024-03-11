package com.sshanti.bank.model.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.sshanti.bank.exceptions.entity.EntityNotFoundException;
import com.sshanti.bank.model.Customer;
import lombok.Data;

import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto {
    private String name;

    private String customerId;

    private String customerType;
    private String gender;

    private String address;
    private int age;
    private Set<AccountDto> accounts;

    public static CustomerDto toDto(Customer customer) throws EntityNotFoundException {
        if (customer == null) throw new EntityNotFoundException("Cannot found the provided customer");
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerId(customer.getFormattedId());
        customerDto.setCustomerType(customer.getCustomerType().name());
        customerDto.setName(customer.getName());
        customerDto.setAddress(customer.getAddress());
        customerDto.setAge(customer.getAge());
        customerDto.setGender(customer.getGender().name());
        return customerDto;
    }
}
