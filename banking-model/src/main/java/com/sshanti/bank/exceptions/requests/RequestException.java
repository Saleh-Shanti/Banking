package com.sshanti.bank.exceptions.requests;

import com.sshanti.bank.exceptions.messages.HTTPRequestMessage;

public class RequestException extends Throwable {
    HTTPRequestMessage message;
    public RequestException(HTTPRequestMessage message) {
        super(String.join(",", message.getErrors()));
        this.message = message;
    }

    public HTTPRequestMessage getErrorMessage() {
        return message;
    }
}
