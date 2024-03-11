package com.sshanti.bank.repository;


import com.sshanti.bank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Set<Account> findByCustomer(String customerId);
    void deleteAllByCustomer(String customerId);
}
