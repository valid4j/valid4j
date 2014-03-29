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
			handleContractViolation(new EnsureViolation(withFormattedMessage(msg, values)));
		}
	}

	@Override
	public void neverGetHere(String msg, Object... values) {
		handleContractViolation(new NeverGetHereViolation(withFormattedMessage(msg, values)));
	}

	@Override
	public void neverGetHere(Throwable t, String msg, Object... values) {
		handleContractViolation(new NeverGetHereViolation(t, withFormattedMessage(msg, values)));
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
