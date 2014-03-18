package org.valid4j;

import java.util.Arrays;
import java.util.IllegalFormatException;

import org.valid4j.exceptions.*;

public class DefaultContractViolationPolicy extends BaseContractViolationPolicy implements ContractViolationPolicy {

	@Override
	public void require(boolean condition, String msg, Object... values) {
		if (!condition) {
			handleContractViolation(new RequireViolation(withFormattedMessage(msg, values)));
		}
	}

	@Override
	public void ensure(boolean condition, String msg, Object... values) {
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

	private static String withFormattedMessage(String msg, Object... values) {
		try {
			return msg != null ? String.format(msg, values) : null;
		} catch (IllegalFormatException e) {
			StringBuilder sb = new StringBuilder(msg);
			sb.append(Arrays.toString(values));
			return sb.toString();
		}
	}

	private static void handleContractViolation(final ContractViolation t) {
		throw t;
	}
}
