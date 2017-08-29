package com.coder4.sbmvt.abc.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SbmvtAbcApplication {

	public static void main(String[] args) {

		SpringApplication application = new SpringApplication(SbmvtAbcApplication.class);

		// To disabled web environment, change `true` to `false`
		application.setWebEnvironment(true);
		application.run(args);

	}
}
