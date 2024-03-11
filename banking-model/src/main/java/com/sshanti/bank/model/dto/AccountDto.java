package com.sshanti.bank.model.dto;


import com.sshanti.bank.model.Account;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDto {

    private String accountId;

    private String customer;

    private BigDecimal balance;

    private String accountStatus;
    private String accountType;

   public static AccountDto toDto(Account account){
        AccountDto ac = new AccountDto();
        ac.setAccountId(account.getFormattedAccountId());
        ac.setCustomer(String.valueOf(account.getCustomer()));
        ac.setAccountStatus(account.getAccountStatus().name());
        ac.setBalance(account.getBalance());
        return ac;
    }

}
