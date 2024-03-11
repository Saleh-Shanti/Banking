package com.sshanti.bank.model;

import com.sshanti.bank.model.dto.CustomerDto;
import com.sshanti.bank.model.enums.CustomerGender;
import com.sshanti.bank.model.enums.CustomerType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "CUSTOMERS")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Enumerated(EnumType.STRING)
    private CustomerType customerType;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    private CustomerGender gender;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Account> accounts = new HashSet<>();

    public static Customer fromDto(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setCustomerType(CustomerType.valueOf(customerDto.getCustomerType().toUpperCase()));
        customer.setName(customerDto.getName());
        customer.setAddress(customerDto.getAddress());
        customer.setAge(customerDto.getAge());
        customer.setGender(CustomerGender.valueOf(customerDto.getGender().toUpperCase()));
        return customer;
    }

    public String getFormattedId(){
        return String.format("%07d", ((Number) id).longValue());
    }
}
