package com.sshanti.bank.exceptions.requests;

import com.sshanti.bank.exceptions.messages.BadRequestMessage;

import java.util.List;

public class BadRequestException extends RequestException {


    public BadRequestException(List<String> errorMessages) {
        super(new BadRequestMessage(errorMessages));
    }
}
