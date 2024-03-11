package com.sshanti.bank.exceptions.messages;

import java.util.List;

public class ServerExceptionMessage implements HTTPRequestMessage{
    private static final int status = 500;
    private List<String> errors;

    public ServerExceptionMessage(List<String> errors) {
        this.errors = errors;
    }

    public int getStatus() {
        return status;
    }

    public List<String> getErrors() {
        return errors;
    }
}
