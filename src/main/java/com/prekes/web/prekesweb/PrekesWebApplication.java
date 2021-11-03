package com.prekes.web.prekesweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // scan for components in current package and below
public class PrekesWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrekesWebApplication.class, args);
	}
}
