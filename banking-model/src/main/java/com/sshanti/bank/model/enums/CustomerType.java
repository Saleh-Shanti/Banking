package com.sshanti.bank.model.enums;

import java.util.Arrays;

public enum CustomerType {
    RETAIL,
    CORPORATE,
    INVESTMENT;

    public static CustomerType getByName(String name) {
        return Arrays.stream(CustomerType.values())
                .filter(t -> t.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}