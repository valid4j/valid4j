package org.valid4j.errors;

/**
 * Exception being thrown to indicate that supposedly unreachable code
 * have been reached, and that this is considered a programming error.
 */
public class NeverGetHereViolation extends ContractViolation {

	private static final long serialVersionUID = -7898390791997251693L;

	public NeverGetHereViolation(Throwable t, String msg) {
		super(t, msg);
	}
}