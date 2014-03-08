package org.valid4j.exceptions;


public class NeverGetHereViolation extends ContractViolation {
	private static final long serialVersionUID = 1L;

	public NeverGetHereViolation(String msg) {
		super(msg);
	}
	public NeverGetHereViolation(Throwable t) {
		super(t);
	}
}