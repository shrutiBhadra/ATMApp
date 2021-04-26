package com.interview.atm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.interview.model.UserSession;
import com.interview.service.ATMService;

@SpringBootTest
class ATMApplicationTests {
	ATMService atmService = new ATMService();
	
	@Test
	void testUserSessionObject() {
		
		
		List<String> input = new ArrayList<>();
		
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
		
		List<UserSession> userSessionList = atmService.instantiateUserSession(input);
		
		//Test 3 object of user session are created.
		assertEquals(3, userSessionList.size(), " size of UserSession object creation should be 3 but it is " + userSessionList.size());
		
	}

	@Test
	void testValidateUserLogin() {
		// will test for manually created session object
		UserSession userSession = new UserSession();
		userSession.setEnteredPin(1234);
		userSession.setExpectedPin(1234);
		atmService.validateUserLogin(userSession);
		assertNull(userSession.getOutput());
	}
	
	@Test
	void testPerformOperation() {
		//perform operations continuously to test subtraction from existing amount.
		atmService.atm.setMoneyInATM(1000);
		UserSession userSession = new UserSession();
		List<String> operations = new ArrayList<>();
		
		//Test data to test happy scenarios.
		operations.add("B");
		operations.add("W 100");
		
		userSession.setCurrentBalance(500);
		userSession.setOverDraft(50);
		userSession.setOperations(operations);
		
		atmService.performOperation(userSession);
		
		assertEquals(400, Integer.valueOf(userSession.getOutput().get(1)), "Balance remaining should be 400 but it is showing as "+ userSession.getOutput().get(0));
		
		assertEquals(900, atmService.atm.getMoneyInATM(), "Money in the atm after withdrawal should be 600 but is is " + atmService.atm.getMoneyInATM());
		
		assertEquals(400, userSession.getCurrentBalance() , "Current balance of current user should be 400 but it is " + userSession.getCurrentBalance());
		
		assertEquals("500", userSession.getOutput().get(0), "output for first 'B' operation is list to show balance before withdrawal  will be output first position because of sequence of operation");
	
		//perform second operation 'W' and 'B'. Just updating 
		
		operations = new ArrayList<>();
		
		//Test data to withdraw more amount than current balance.
		operations.add("B");
		operations.add("W 500");
		userSession.setOperations(operations);
		userSession.setOutput(new ArrayList<>());
		
		atmService.performOperation(userSession);
		
		assertEquals("400", userSession.getOutput().get(0),"");
		assertEquals("FUNDS_ERR", userSession.getOutput().get(1),"Error FUNDS_ERR should be displayed since withdrawal amount is more than current balance");
		
	}
	
}
