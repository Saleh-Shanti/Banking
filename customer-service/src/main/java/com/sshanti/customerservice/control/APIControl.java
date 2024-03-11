package com.sshanti.customerservice.control;

import com.sshanti.bank.messages.ResponseMessage;
import com.sshanti.bank.model.dto.AccountDto;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

@Component
public class APIControl {
    RestTemplate restTemplate = new RestTemplate();

    @PostConstruct
    void init() {
        restTemplate = new RestTemplateBuilder().build();
    }

    public ResponseEntity<Object> callAPI(String url, HttpMethod method, Set<AccountDto> data) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Set<AccountDto>> requestEntity = new HttpEntity<>(data, headers);

        return restTemplate.exchange(
                url,
                method,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
    }
}
