package org.valid4j.exceptions;

public class ContractViolation extends RuntimeException {
	
	private static final long serialVersionUID = 4277032519114670211L;
	
	protected ContractViolation(String msg) {
		super(msg);
	}
	protected ContractViolation(Throwable t) {
		super(t);
	}
}