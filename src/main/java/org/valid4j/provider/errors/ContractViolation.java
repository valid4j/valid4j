package org.valid4j.provider.errors;

/**
 * Exception thrown to indicate that a contract violation have been detected.
 * Contract violations are considered to be programming errors, and hence
 * should not be caught and recovered from in normal application flow.
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