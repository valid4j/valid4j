package org.valid4j.exceptions;


public class NeverGetHereViolation extends ContractViolation {

	private static final long serialVersionUID = -7898390791997251693L;

	public NeverGetHereViolation(String msg) {
		super(msg);
	}
		
	public NeverGetHereViolation(Throwable t, String msg) {
		super(t, msg);
	}
}