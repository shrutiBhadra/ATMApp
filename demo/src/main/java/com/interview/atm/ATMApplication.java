package com.interview.atm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.interview.model.UserSession;
import com.interview.service.ATMService;

@SpringBootApplication
public class ATMApplication {

	public static void main(String[] args) {
		SpringApplication.run(ATMApplication.class, args);
		
		List<String> input = new ArrayList<>();
		List<UserSession> userSessionList = new ArrayList<>();
		List<String> output = new ArrayList<>();
		
		input.add("8000");
		input.add("");
		input.add("12345678 1234 1234");
		input.add("500 100");
		input.add("B");
		input.add("W 100");
		input.add("");
		input.add("87654321 4321 4321");
		input.add("100 0");
		input.add("W 10");
		input.add("");
		input.add("87654321 4321 4321");
		input.add("0 0");
		input.add("W 10");
		input.add("B");

		ATMService atmService = new ATMService();
		userSessionList = atmService.instantiateUserSession(input);
		for (UserSession userSessionIterator : userSessionList) {
			atmService.validateUserLogin(userSessionIterator);
			if(userSessionIterator.getOutput() == null) {
				atmService.performOperation(userSessionIterator);
			}for (String string : userSessionIterator.getOutput()) {
				output.add(string);
			}
		}
		
		for (String string : output) {
			System.out.println(string);
		}

	
	}

}
