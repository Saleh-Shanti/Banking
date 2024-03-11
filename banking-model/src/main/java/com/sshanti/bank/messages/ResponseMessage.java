package com.sshanti.bank.messages;

import lombok.Data;

import java.util.List;

@Data
public class ResponseMessage {
    private int code;
    private Object data;
    private List<String> messages;
}
