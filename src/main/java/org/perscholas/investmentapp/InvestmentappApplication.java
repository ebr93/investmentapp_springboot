package org.perscholas.investmentapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InvestmentappApplication {

	public static void main(String[] args) {

		try {
			SpringApplication app = new SpringApplication(InvestmentappApplication.class);
			app.run(args);
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}

}
