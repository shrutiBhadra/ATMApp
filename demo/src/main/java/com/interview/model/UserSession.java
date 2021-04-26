package com.interview.model;

import java.util.List;

public class UserSession {

	private int accountNumber;
	
	// pin entered by user
	private int enteredPin;
	
	// pin which is correct
	private int expectedPin;
	
	// current balance in account for user
	private int currentBalance;
	
	// over draft amount above current balance which can removed by user.
	private int overDraft;
	
	// list of operation like 'W' and 'B' need to maintain sequence to show output corresponding to input.
	private List<String> operations;

	//list of output which will be in sequence as per the input provided.
	private List<String> output;
	
	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public int getExpectedPin() {
		return expectedPin;
	}
	
	public void setExpectedPin(int expectedPin) {
		this.expectedPin = expectedPin;
	}

	public int getEnteredPin() {
		return enteredPin;
	}

	public void setEnteredPin(int enteredPin) {
		this.enteredPin = enteredPin;
	}

	public int getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(int currentBalance) {
		this.currentBalance = currentBalance;
	}

	public int getOverDraft() {
		return overDraft;
	}

	public void setOverDraft(int overDraft) {
		this.overDraft = overDraft;
	}
	
	public List<String> getOutput() {
		return output;
	}

	public void setOutput(List<String> output) {
		this.output = output;
	}
	
	public List<String> getOperations() {
		return operations;
	}

	public void setOperations(List<String> operations) {
		this.operations = operations;
	}


}
