package com.sshanti.bank.exceptions.messages;

import java.util.List;

public class BadRequestMessage implements HTTPRequestMessage{
    private static final int status = 400;
    private List<String> errors;

    public BadRequestMessage(List<String> errors) {
        this.errors = errors;
    }

    public int getStatus() {
        return status;
    }

    public List<String> getErrors() {
        return errors;
    }
}
