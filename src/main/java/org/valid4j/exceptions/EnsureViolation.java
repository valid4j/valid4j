package org.valid4j.exceptions;

/**
 * Exception thrown to indicate that a programming error have been detected,
 * on the suppliers part. A postcondition does not hold.
 */
public class EnsureViolation extends ContractViolation {

  private static final long serialVersionUID = 7975841429066234859L;

  public EnsureViolation(String msg) {
		super(msg);
	}
}