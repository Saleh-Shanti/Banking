package com.sshanti.bank.exceptions.requests;

public enum Messages {
    GENERAL_ERROR_MESSAGE("Oops, Something went wrong .{}"),
    INVALID_VALUES_ERROR_MESSAGE("Oops, Some values are invalid.{}"),
    CUSTOMER_NOT_FOUND("Customer with id %s not found."),
    CUSTOMER_ADDED_SUCCESSFULLY("Customer %s has been added with id %s"),
    CUSTOMER_UPDATED_SUCCESSFULLY("Customer %s with id %s has been updated."),
    CUSTOMER_DELETED_SUCCESSFULLY("Customer with id %s has been deleted."),
    EMPTY_NAME_MESSAGE("Name cannot be empty."),
    INVALID_CUSTOMER_TYPE("Invalid Customer Type."),
    INVALID_CUSTOMER_Gender("Invalid Customer Gender."),
    AGE_ERROR_MESSAGE("Age cannot be under 18"),
    ERROR_PERSISTING_MESSAGE("Couldn't Save customer with data %s"),
    ACCOUNT_NOT_FOUND("Account with id %s not found."),
    ACCOUNT_DELETED_SUCCESSFULLY("Account with id %s for customer %s has been deleted."),
    ACCOUNTS_ADDED_SUCCESSFULLY("Accounts for customer %s has been added successfully"),
    ACCOUNTS_UPDATED_SUCCESSFULLY("Account %d has been added successfully"),
    INVALID_BALANCE_ACCOUNT("Balance cannot be zero"),
    INVALID_SALARY_ACCOUNT_TYPE("Customer can only have one salary account");
    final String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
