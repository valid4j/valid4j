package org.valid4j.exceptions;

/**
 * Exception thrown to indicate that a programming error have been detected.
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