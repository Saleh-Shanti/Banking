package com.sshanti.bank.model.dto;


import com.sshanti.bank.exceptions.entity.EntityNotFoundException;
import com.sshanti.bank.model.Account;
import com.sshanti.bank.model.enums.AccountType;
import lombok.Data;

import java.math.BigDecimal;

import static com.sshanti.bank.exceptions.requests.Messages.ACCOUNT_NOT_FOUND;

@Data
public class AccountDto {

    private String accountId;

    private String customer;

    private BigDecimal balance;

    private String accountStatus;
    private String accountType;

    public static AccountDto toDto(Account account) throws EntityNotFoundException {
        if (account == null) throw new EntityNotFoundException(ACCOUNT_NOT_FOUND.getMessage());
        AccountDto ac = new AccountDto();
        ac.setAccountId(account.getFormattedAccountId());
        ac.setCustomer(String.valueOf(account.getCustomer()));
        ac.setAccountStatus(account.getAccountStatus().name());
        ac.setAccountType(account.getType().name());
        ac.setBalance(account.getBalance());
        return ac;
    }

}
