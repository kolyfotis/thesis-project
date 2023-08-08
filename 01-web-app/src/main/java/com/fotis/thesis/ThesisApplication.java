package com.fotis.thesis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Calendar;

@SpringBootApplication
public class ThesisApplication {

	public static void main(String[] args) {
		System.out.printf("%s: Starting Application...%n", Calendar.getInstance().getTime());
		SpringApplication.run(ThesisApplication.class, args);
	}

}
