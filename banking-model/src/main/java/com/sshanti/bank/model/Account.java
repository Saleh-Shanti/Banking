package com.sshanti.bank.model;


import com.sshanti.bank.model.dto.AccountDto;
import com.sshanti.bank.model.enums.AccountStatus;
import com.sshanti.bank.model.enums.AccountType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "ACCOUNTS")
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(insertable = false, updatable = false)
    private String accountNumber;

    @Column(nullable = false)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    private String customer;

    public String getFormattedAccountId() {
        return String.format("%07d%03d", Long.valueOf(customer), ((Number) id).longValue());
    }

    public static Account fromDto(AccountDto dto) {
        Account acc = new Account();
//        acc.setCustomer(Long.parseLong(dto.getCustomer()));
        acc.setType(AccountType.valueOf(dto.getAccountType().toUpperCase()));
        acc.setAccountStatus(AccountStatus.valueOf(dto.getAccountStatus().toUpperCase()));
        acc.setBalance(dto.getBalance());
        return acc;
    }


}
