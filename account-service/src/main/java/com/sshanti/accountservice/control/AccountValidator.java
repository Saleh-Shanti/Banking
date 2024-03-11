package com.sshanti.accountservice.control;

import com.sshanti.bank.model.Account;
import com.sshanti.bank.model.dto.AccountDto;
import com.sshanti.bank.model.enums.AccountStatus;
import com.sshanti.bank.model.enums.AccountType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static com.sshanti.bank.exceptions.requests.Messages.INVALID_BALANCE_ACCOUNT;
import static com.sshanti.bank.exceptions.requests.Messages.INVALID_SALARY_ACCOUNT_TYPE;

@Component
public class AccountValidator {
    public List<String> isValidAccounts(Set<Account> customerAccounts, Set<AccountDto> accounts) {
        List<String> errorMessages = new ArrayList<>();

        accounts
                .forEach(account -> {
                    //Validate accounts type
                    isValidAccountType(account, errorMessages);
                    //validate balance foreach account
                    isValidBalance(account, errorMessages);
                    //validate account status
                    isValidAccountStatus(account, errorMessages);
                });


        // validate if there is only one salary acc
        isOneSalaryAccountType(customerAccounts, accounts, errorMessages);


        return errorMessages;
    }

    private void isValidAccountStatus(AccountDto account, List<String> errorMessages) {
        if (AccountStatus.getByName(account.getAccountStatus().toUpperCase()) == null)
            errorMessages.add(String.format("Invalid account status %s", account.getAccountStatus()));

    }

    private void isValidAccountType(AccountDto account, List<String> errorMessages) {
        if (AccountType.getByName(account.getAccountType().toUpperCase()) == null)
            errorMessages.add(String.format("Invalid account type %s", account.getAccountType()));
    }

    private void isOneSalaryAccountType(Set<Account> customerAccounts, Set<AccountDto> accounts, List<String> errorMessages) {
        Predicate<Account> isSalaryAccount = acc -> acc.getType().equals(AccountType.SALARY);
        Predicate<AccountDto> isSalaryAccountDto = acc -> AccountType.SALARY.name().equalsIgnoreCase(acc.getAccountType());

        int customerSalaryAccountCount = customerAccounts.stream().filter(isSalaryAccount).toList().size();
        int customerSalaryAccountDtoCount = accounts.stream().filter(isSalaryAccountDto).toList().size();

        if (customerSalaryAccountCount > 0 && customerSalaryAccountDtoCount > 0) {
            errorMessages.add(INVALID_SALARY_ACCOUNT_TYPE.getMessage());
        } else if (customerSalaryAccountDtoCount > 1)
            errorMessages.add(INVALID_SALARY_ACCOUNT_TYPE.getMessage());

    }

    private void isValidBalance(AccountDto account, List<String> errorMessages) {
        if (account.getBalance().intValue() == 0)
            errorMessages.add(INVALID_BALANCE_ACCOUNT.getMessage());
    }
}
