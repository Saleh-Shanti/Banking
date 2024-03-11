package com.sshanti.accountservice.boundary;

import com.sshanti.accountservice.control.AccountControl;
import com.sshanti.bank.exceptions.entity.EntityNotFoundException;
import com.sshanti.bank.exceptions.requests.BadRequestException;
import com.sshanti.bank.exceptions.requests.ServerException;
import com.sshanti.bank.model.dto.AccountDto;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;

import static com.sshanti.bank.exceptions.requests.Messages.*;


@RestController
@RequestMapping("/api/v1/customers/{customerId}/accounts")
public class AccountResource {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AccountResource.class);


    @Autowired
    private AccountControl accountControl;


    @GetMapping("/{accountId}")
    @PreAuthorize("hasAnyRole('READ','ADMIN')")
    @ApiOperation(value = "Get a account by ID", notes = "Retrieve information about a accounts by providing their ID.")
    public ResponseEntity<Set<AccountDto>> getAccountById(@PathVariable Long accountId) {

        try {
            logger.info("New find account by id request with id {}.", accountId);
            AccountDto account = accountControl.getAccountById(accountId);
            return ResponseEntity.ok(Collections.singleton(account));
        } catch (EntityNotFoundException enf) {
            logger.warn(String.format(ACCOUNT_NOT_FOUND.getMessage(), accountId));
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole('READ','ADMIN')")
    @ApiOperation(value = "Get a accounts by ID", notes = "Retrieve information about a accounts by providing the customer ID.")
    public ResponseEntity<Object> getAccountByCustomerId(@PathVariable Long customerId) {
        try {
            logger.info("New find accounts by customer id request with id {}.", customerId);
            Set<AccountDto> accounts = accountControl.getAccountsByCustomerId(customerId);
            return ResponseEntity.ok(accounts);
        } catch (EntityNotFoundException enf) {
            logger.warn(String.format(ACCOUNT_NOT_FOUND.getMessage(), customerId));
            return ResponseEntity.notFound().build();
        } catch (ServerException e) {
            logger.error(GENERAL_ERROR_MESSAGE.getMessage(), e.getErrorMessage().getErrors());
            return ResponseEntity.internalServerError().body(e.getErrorMessage());
        } catch (Exception e) {
            logger.error(GENERAL_ERROR_MESSAGE.getMessage(), e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADD', 'ADMIN')")
    @ApiOperation(value = "Creating a new accounts", notes = "Creating a new accounts.")
    public ResponseEntity<Object> createAccounts(@RequestBody Set<AccountDto> accountDtoSet, @PathVariable Long customerId) {

        try {
            logger.info("New add accounts for customer {} has been received.", customerId);
            accountControl.createAccounts(customerId, accountDtoSet);
            logger.info(String.format(ACCOUNTS_ADDED_SUCCESSFULLY.getMessage(), customerId));
            return ResponseEntity.status(201).body(String.format(ACCOUNTS_ADDED_SUCCESSFULLY.getMessage(), customerId));
        } catch (BadRequestException be) {
            logger.error(INVALID_VALUES_ERROR_MESSAGE.getMessage(), be.getErrorMessage().getErrors());
            return ResponseEntity.badRequest().body(be.getErrorMessage());
        } catch (EntityNotFoundException ef) {
            logger.warn(String.format(CUSTOMER_NOT_FOUND.getMessage(), customerId));
            return ResponseEntity.notFound().build();
        } catch (ServerException e) {
            logger.error(GENERAL_ERROR_MESSAGE.getMessage(), e.getErrorMessage(), e);
            return ResponseEntity.internalServerError().body(e.getErrorMessage());
        } catch (Exception e) {
            logger.error(GENERAL_ERROR_MESSAGE.getMessage(), e.getCause().getMessage(), e);
            return ResponseEntity.internalServerError().body(e.getCause().getMessage());
        }
    }


    @PutMapping("/{accountId}")
    @PreAuthorize("hasAnyRole('UPDATE', 'ADMIN')")
    @ApiOperation(value = "Updating account by ID.", notes = "Update an existing account.")
    public ResponseEntity<Object> updateAccount(@PathVariable Long accountId, @RequestBody AccountDto updatedAccountDto) {
        try {
            logger.info("New update account with id {} request.", accountId);
            accountControl.updateAccount(accountId, updatedAccountDto);
            return ResponseEntity.ok(String.format(ACCOUNTS_UPDATED_SUCCESSFULLY.getMessage(), accountId));
        } catch (BadRequestException be) {
            logger.error(INVALID_VALUES_ERROR_MESSAGE.getMessage(), be.getErrorMessage().getErrors());
            return ResponseEntity.badRequest().body(be.getErrorMessage());
        } catch (EntityNotFoundException e) {
            logger.warn(String.format(ACCOUNT_NOT_FOUND.getMessage(), accountId));
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error(GENERAL_ERROR_MESSAGE.getMessage(), e.getCause().getMessage(), e);
            return ResponseEntity.internalServerError().body(e.getCause().getMessage());
        }
    }


    @DeleteMapping("/{accountId}")
    @PreAuthorize("hasAnyRole('DELETE', 'ADMIN')")
    @ApiOperation(value = "Delete a account by ID", notes = "Delete account by providing their ID.")
    public ResponseEntity<Object> deleteAccount(@PathVariable Long accountId, @PathVariable Long customerId) {
        try {
            logger.info("New delete account {} for customer {} request.", accountId, customerId);
            accountControl.deleteAccount(accountId, customerId);
            return ResponseEntity.status(HttpStatus.OK).body(String.format(ACCOUNT_DELETED_SUCCESSFULLY.getMessage(), accountId, customerId));
        } catch (ServerException | Exception e) {
            logger.warn(String.format(GENERAL_ERROR_MESSAGE.getMessage(), accountId));
            return ResponseEntity.internalServerError().build();
        }
    }


    @DeleteMapping("")
    @PreAuthorize("hasAnyRole('DELETE', 'ADMIN')")
    @ApiOperation(value = "Delete a accounts by customer ID", notes = "Delete account by customer ID.")
    public ResponseEntity<Object> deleteAccountsByCustomerId(@PathVariable Long customerId) {
        try {
            logger.info("New delete account {} for customer {} request.", customerId, customerId);
            accountControl.deleteAccount(customerId);
            return ResponseEntity.status(HttpStatus.OK).body(String.format(ACCOUNT_DELETED_SUCCESSFULLY.getMessage(), "", customerId));
        } catch (ServerException | Exception e) {
            logger.warn(String.format(GENERAL_ERROR_MESSAGE.getMessage(), customerId));
            return ResponseEntity.internalServerError().build();
        }
    }
}
