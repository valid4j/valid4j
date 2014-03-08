package org.valid4j;

import org.valid4j.exceptions.*;

public class DefaultContractViolationPolicy extends BaseContractViolationPolicy implements ContractViolationPolicy {

	@Override
	public void require(boolean condition, String msg) {
		if (!condition) {
			handleContractViolation(new RequireViolation(withMessage("require violation", msg)));
		}
	}

	@Override
	public void ensure(boolean condition, String msg) {
		if (!condition) {
			handleContractViolation(new EnsureViolation(withMessage("ensure violation", msg)));
		}
	}

	@Override
	public void neverGetHere(String msg) {
		handleContractViolation(new NeverGetHereViolation(withMessage("never get here", msg)));
	}

	@Override
	public void neverGetHere(Throwable t) {
		handleContractViolation(new NeverGetHereViolation(t));
	}

	private static void handleContractViolation(final ContractViolation t) {
		throw t;
	}
}
