package org.valid4j.exceptions;

/**
 * Exception thrown to indicate that a programming error have been detected,
 * on the clients part. A precondition does not hold.
 */
public class RequireViolation extends ContractViolation {

  private static final long serialVersionUID = -8916851351275789989L;

  public RequireViolation(String msg) {
		super(msg);
	}
}