package com.sshanti.bank.model.enums;

import java.util.Arrays;

public enum AccountType {
    SALARY,
    SAVING,
    INVESTMENT;

    public static AccountType getByName(String name) {
        return Arrays.stream(AccountType.values())
                .filter(t -> t.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
