package com.sshanti.accountservice.control;


import com.sshanti.bank.exceptions.entity.EntityNotFoundException;
import com.sshanti.bank.exceptions.requests.BadRequestException;
import com.sshanti.bank.exceptions.requests.ServerException;
import com.sshanti.bank.model.Account;
import com.sshanti.bank.model.Customer;
import com.sshanti.bank.model.dto.AccountDto;
import com.sshanti.bank.service.AccountService;
import com.sshanti.bank.service.CustomerService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sshanti.bank.exceptions.requests.Messages.ACCOUNT_NOT_FOUND;


@Component
public class AccountControl {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AccountControl.class);

    @Autowired
    AccountValidator accountValidator;
    @Autowired
    AccountService accountService;
    @Autowired
    CustomerService customerService;

    public AccountDto getAccountById(Long accountId) throws EntityNotFoundException {
        return AccountDto.toDto(accountService.getAccountById(accountId));
    }

    public Set<AccountDto> getAccountsByCustomerId(Long customerId) throws EntityNotFoundException, ServerException {
        try {
            logger.info("Checking accounts for customer with id {}", customerId);
            Set<Account> accounts = accountService.findAccountsByCustomerId(customerId);

            if (accounts == null || accounts.isEmpty())
                throw new EntityNotFoundException(ACCOUNT_NOT_FOUND.getMessage());

            logger.info("{} accounts has been found", accounts.size());
            return accounts.stream()
                    .map(acc -> {
                        try {
                            return AccountDto.toDto(acc);
                        } catch (Exception e) {
                            logger.warn(e.getMessage());
                            return null;
                        }
                    })
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServerException(Collections.singletonList(e.getMessage()));

        }
    }

    public void createAccounts(Long customerId, Set<AccountDto> accountDtoSet) throws BadRequestException, EntityNotFoundException, ServerException {

        logger.info("Checking customer if exists.");
        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null)
            throw new EntityNotFoundException(String.format("Cannot found the provided customer with id %d", customerId));

        logger.info("Validating accounts for customer {}", customer);
        List<String> errorMessages = accountValidator.isValidAccounts(customer.getAccounts(), accountDtoSet);

        if (!errorMessages.isEmpty()) {
            logger.error("Error creating accounts for customer {} due to :", customerId);
            for (String message : errorMessages) {
                logger.error(message);
            }

            throw new BadRequestException(errorMessages);
        }

        logger.info("Saving accounts for customer {}", customerId);
        accountDtoSet.forEach(acc -> acc.setCustomer(String.valueOf(customerId)));

        Set<Account> accounts = accountDtoSet.stream()
                .map(Account::fromDto)
                .collect(Collectors.toSet());
        accountService.createAccounts(accounts);

    }

    public AccountDto updateAccount(Long accountId, AccountDto updatedAccountDto) throws BadRequestException, EntityNotFoundException {
        logger.info("Validating Updated account Data ...");
        List<String> errorMessages = accountValidator.isValidAccounts(Collections.EMPTY_SET, Set.of(updatedAccountDto));
        if (!errorMessages.isEmpty()) {
            logger.error("Error updating account due to :");
            for (String message : errorMessages) {
                logger.error(message);
            }

            throw new BadRequestException(errorMessages);
        }

        return AccountDto.toDto(accountService.updateAccount(accountId, Account.fromDto(updatedAccountDto)));
    }

    public void deleteAccount(Long accountId, Long customerId) throws ServerException {
        try {
            logger.info("Deleting customer with id {}", customerId);
            Account accountToDelete = accountService.getAccountById(accountId);
            if (accountToDelete == null)
                logger.warn("Account with id {} not found", accountId);

            accountService.deleteAccountById(accountId);
            logger.info("account with id {} , Deleted successfully.", customerId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServerException(Collections.singletonList(e.getMessage()));
        }


    }

    public void deleteAccount(Long customerId) throws ServerException {
        try {
            logger.info("Deleting accounts for customer {}", customerId);
            logger.info("Checking customer if exists.");
            Customer customer = customerService.getCustomerById(customerId);
            if (customer == null)
                throw new EntityNotFoundException(String.format("Cannot found the provided customer with id %d", customerId));

            accountService.deleteAccountsByCustomer(customerId);
            logger.info("Accounts for customer {} has been deleted", customerId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServerException(Collections.singletonList(e.getMessage()));
        }

    }
}
