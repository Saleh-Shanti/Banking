package com.sshanti.bank.exceptions.requests;

import com.sshanti.bank.exceptions.messages.ServerExceptionMessage;

import java.util.List;

public class ServerException extends RequestException {
    public ServerException(List<String> errorMessages) {
        super(new ServerExceptionMessage(errorMessages));
    }
}
