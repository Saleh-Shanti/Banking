package com.sshanti.bank.model.enums;

import java.util.Arrays;

public enum CustomerGender {
    MALE,
    FEMALE;

    public static CustomerGender getByName(String name) {
        return Arrays.stream(CustomerGender.values())
                .filter(t -> t.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
