package com.sshanti.bank.service;


import com.sshanti.bank.model.Account;
import com.sshanti.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    public Account getAccountById(Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        return optionalAccount.orElse(null);
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }
    public List<Account> saveAccounts(Set<Account> accounts) {
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
            return null;
        }
    }

}
