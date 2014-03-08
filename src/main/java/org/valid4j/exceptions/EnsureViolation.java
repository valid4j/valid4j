package org.valid4j.exceptions;


public class EnsureViolation extends ContractViolation {
	private static final long serialVersionUID = 1L;

	public EnsureViolation(String msg) {
		super(msg);
	}
}