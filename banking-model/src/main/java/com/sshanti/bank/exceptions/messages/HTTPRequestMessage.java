package com.sshanti.bank.exceptions.messages;

import java.util.List;

public interface HTTPRequestMessage {
    int getStatus();
    List<String> getErrors();

}
