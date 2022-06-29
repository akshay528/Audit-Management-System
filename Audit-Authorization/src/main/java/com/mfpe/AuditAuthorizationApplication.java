package com.mfpe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuditAuthorizationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuditAuthorizationApplication.class, args);
		System.out.println("Audit-Authorization MS - Started");
	}

}
