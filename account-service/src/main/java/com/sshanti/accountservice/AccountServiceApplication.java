package com.sshanti.accountservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.sshanti"})
@EntityScan(basePackages = {"com.sshanti.bank.model"})
@EnableJpaRepositories(basePackages = {"com.sshanti.bank.repository"})
@EnableDiscoveryClient
public class AccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
		System.out.println("---------------- Account Service Started ----------------");
	}

}
