package com.interview.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.interview.model.ATM;
import com.interview.model.ErrorConstants;
import com.interview.model.OperationConstants;
import com.interview.model.UserSession;

public class ATMService {

	public ATM atm = new ATM();

	/**
	 * Instantiates the user session object which includes all operations performed per user.
	 * Assumption - sequence of input will be as mentioned in Doc-ATM Test.
	 * @param input the list of string entered by user.
	 * @return 		list of initiated UserSession object as provided by user.
	 */
	public List<UserSession> instantiateUserSession(List<String> input) {
		List<UserSession> userSessionList = new ArrayList<>();
		atm.setMoneyInATM(Integer.valueOf(input.get(0)));
		UserSession userSession = new UserSession();
		
		// As per 'ATM-doc' input list will sequence of operations.
		// Iteration to have a userSession object with resective value.
		for (int i = 1; i < input.size(); i++) {
			userSession = new UserSession();
			if (input.get(i).isBlank()) {
				String loginString = input.get(i + 1);
			
				extractUserLogin(loginString, userSession);

				String accountFacility = input.get(i + 2);
				List<String> accountFacilityList = Stream.of(accountFacility.split(" ", -1))
						.collect(Collectors.toList());
				
				setAccountFacility(accountFacilityList, userSession);
				
				// since sequence of input is maintained, hard coded as 3 to get operations list. 
				i = i + 3;
				List<String> operations = new ArrayList<String>();
				while (i < input.size() && !input.get(i).isBlank()) {
					operations.add(input.get(i));
					i++;
				}
				userSession.setOperations(operations);
				i--;
				userSessionList.add(userSession);
		
			}
		}
		return userSessionList;
	}

	/**
	 * Extracts login credentials from user input and sets in UserSession object.
	 * @param logingString includes account number, entered pin and actual pin separated by space.
	 * @param UserSession  userSession for initiating account number, expected pin and entered pin.
	 */
	private void extractUserLogin(String logingString, UserSession userSession) {
		List<String> loginList = Stream.of(logingString.split(" ", -1)).collect(Collectors.toList());
		userSession.setAccountNumber(Integer.valueOf(loginList.get(0)));
		userSession.setExpectedPin(Integer.valueOf(loginList.get(1)));
		userSession.setEnteredPin(Integer.valueOf(loginList.get(2)));
	}

	/**
	 * Instantiates user's account balance details.
	 * @param logingString includes available balance and overdraft facility amount separated by space.
	 * @param UserSession  userSession object for updating account details.
	 */
	private void setAccountFacility(List<String> accountFacilityList, UserSession userSession) {
		userSession.setCurrentBalance(Integer.valueOf(accountFacilityList.get(0)));
		userSession.setOverDraft(Integer.valueOf(accountFacilityList.get(1)));
	}

	/**
	 * Validates login credential of user based on entered pin.
	 * @param logingString includes available balance and overdraft facility amount separated by space.
	 * @param UserSession  login credentials updated userSession object for validating user credentials..
	 */
	public void validateUserLogin(UserSession userSession) {
		if (!(userSession.getEnteredPin() == userSession.getExpectedPin())) {
			List<String> outputList = new ArrayList<String>();
			outputList.add("ACCOUNT_ERR");
			userSession.setOutput(outputList);
		}
	}

	/**
	 * Perform operation withdraw and balance according to input 'W' and 'B' entered by user. 
	 * 'W' stands for withdraw and 'B' stands for Balance.
	 * @param UserSession login credentials and account balance details updated userSession object 
	 * 		  for performing Withdraw and balance operations.
	  */
	public void performOperation(UserSession userSession) {
		List<String> operations = userSession.getOperations();
		for (String operationString : operations) {
			List<String> singleOperationList = Stream.of(operationString.split(" ", -1)).collect(Collectors.toList());

			if (singleOperationList.get(0).startsWith(OperationConstants.WITHDRAW)) {
				withdrawAmount(Integer.valueOf(singleOperationList.get(1)), userSession);
			}

			if (singleOperationList.get(0).equalsIgnoreCase(OperationConstants.BALANCE)) {
				List<String> output = new ArrayList<String>();
				if (userSession.getOutput() != null) {
					output = userSession.getOutput();
				}
				output.add(String.valueOf(userSession.getCurrentBalance()));
				userSession.setOutput(output);
			}
		}
	}

	/**
	 * Withdrawing amount based on certain condition. If withdraw amount is more than (balance + overdraft), then it should display 'FUND_ERR'.
	 * It should display 'ATM_ERR' if withdrawal amount is more than amount available in ATM machine.
	 * @param withdrawalAmount amount to be withdraw by user.
	 * @param UserSession login credentials, account balance and performance details updated userSession object 
	 * 		  for Withdrawal operation.
	 */
	private void withdrawAmount(int withdrawalAmount, UserSession userSession) {
		List<String> output = new ArrayList<>();
		if (userSession.getOutput() != null) {
			output = userSession.getOutput();
		}
		if (withdrawalAmount > atm.getMoneyInATM()) {
			output.add(ErrorConstants.ATM_ERR);
		} else if (withdrawalAmount > (userSession.getCurrentBalance() + userSession.getOverDraft())) {
			output.add(ErrorConstants.FUNDS_ERR);
		} else {
			int balance = Integer.valueOf(userSession.getCurrentBalance() - withdrawalAmount);
			atm.setMoneyInATM(atm.getMoneyInATM()-withdrawalAmount);
			userSession.setCurrentBalance(balance);
			output.add(String.valueOf(balance));
		}
		userSession.setOutput(output);
	}

}
