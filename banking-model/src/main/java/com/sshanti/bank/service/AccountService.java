package com.sshanti.bank.service;


import com.sshanti.bank.model.Account;
import com.sshanti.bank.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import static com.sshanti.bank.exceptions.requests.Messages.ACCOUNT_NOT_FOUND;

@Service
public class AccountService {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AccountService.class);


    @Autowired
    private AccountRepository accountRepository;

    public Account getAccountById(Long id) throws EntityNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        return optionalAccount.orElse(null);
    }


    public Set<Account> findAccountsByCustomerId(Long customerId) {
        return accountRepository.findByCustomer(String.valueOf(customerId));
    }

    public List<Account> createAccounts(Set<Account> accounts) {
        return accountRepository.saveAll(accounts);
    }

    public Account updateAccount(Long id, Account account) {
        Optional<Account> optionalExistingAccount = accountRepository.findById(id);

        if (optionalExistingAccount.isPresent()) {
            Account existingAccount = optionalExistingAccount.get();
            existingAccount.setBalance(account.getBalance());
            existingAccount.setAccountStatus(account.getAccountStatus());
            return accountRepository.save(existingAccount);
        } else {
            logger.warn(String.format(ACCOUNT_NOT_FOUND.getMessage(), id));
            return null;
        }
    }

    public void deleteAccountById(Long accountId) {
        accountRepository.deleteById(accountId);
    }

    public void deleteAccountsByCustomer(Long customerId) {
        accountRepository.deleteAllByCustomer(String.valueOf(customerId));
    }
}
