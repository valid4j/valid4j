package org.valid4j.exceptions;


public class RequireViolation extends ContractViolation {
	private static final long serialVersionUID = 1L;

	public RequireViolation(String msg) {
		super(msg);
	}
}