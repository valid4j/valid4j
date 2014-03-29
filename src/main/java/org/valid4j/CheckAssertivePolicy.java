package org.valid4j;

import java.util.Arrays;
import java.util.IllegalFormatException;

import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.valid4j.exceptions.*;

/**
 * An implementation of the assertive policy that checks the given
 * conditions for violations.
 */
public class CheckAssertivePolicy implements AssertivePolicy {

	@Override
	public void require(boolean condition, String msg, Object... values) {
		if (!condition) {
			handleRequireViolation(msg, values);
		}
	}

	@Override
	public void require(Object o, Matcher<?> matcher) {
		if (!matcher.matches(o)) {
			handleRequireViolation(o, matcher);
		}
	}

	@Override
	public void ensure(boolean condition, String msg, Object... values) {
		if (!condition) {
			handleEnsureViolation(msg, values);
		}
	}


	@Override
	public void ensure(Object o, Matcher<?> matcher) {
		if (!matcher.matches(o)) {
			handleEnsureViolation(o, matcher);
		}
	}

	@Override
	public void neverGetHere(Throwable cause, String msg, Object... values) {
		handleNeverGetHereViolation(cause, msg, values);
	}

	// TODO: Move all the handle*() methods into a new interface, ContractViolationPolicy
	private void handleRequireViolation(String msg, Object... values) {
		handleContractViolation(new RequireViolation(withFormattedMessage(msg, values)));
	}

	private void handleRequireViolation(Object o, Matcher<?> matcher) {
		handleContractViolation(new RequireViolation(withMismatchMessageOf(o, matcher)));
	}

	private void handleEnsureViolation(String msg, Object... values) {
		handleContractViolation(new EnsureViolation(withFormattedMessage(msg, values)));
	}

	private void handleEnsureViolation(Object o, Matcher<?> matcher) {
		handleContractViolation(new EnsureViolation(withMismatchMessageOf(o, matcher)));
	}

	private void handleNeverGetHereViolation(Throwable t, String msg, Object... values) {
		handleContractViolation(new NeverGetHereViolation(t, withFormattedMessage(msg, values)));
	}

	private static String withMismatchMessageOf(Object actual, Matcher<?> matcher) {
		return new StringDescription().
				appendText("expected: ").
				appendDescriptionOf(matcher).appendText("\n but was: ").
				appendValue(actual).toString();
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
