package com.sshanti.customerservice.util;

public enum URLMapping {

    ADD_NEW_ACCOUNTS("/api/v1/customers/%s/accounts");
    String url;

    URLMapping(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
