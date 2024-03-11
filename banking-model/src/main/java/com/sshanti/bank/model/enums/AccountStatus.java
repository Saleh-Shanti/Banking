package com.sshanti.bank.model.enums;

import java.util.Arrays;

public enum AccountStatus {
    ACTIVE,
    INACTIVE;

    public static AccountStatus getByName(String name) {
        return Arrays.stream(AccountStatus.values())
                .filter(t -> t.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}