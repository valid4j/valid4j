package org.valid4j.exceptions;

/**
 * Exception thrown to indicate that a contract violation have been detected.
 * Contract violations are considered to be programming errors, and hence
 * should not be catched and recovered from in normal application flow.
 */
public class ContractViolation extends AssertionError {
	
	private static final long serialVersionUID = 4277032519114670211L;
	
	protected ContractViolation(String msg) {
		super(msg);
	}
		
	protected ContractViolation(Throwable t, String msg) {
		super(msg, t);
	}
}