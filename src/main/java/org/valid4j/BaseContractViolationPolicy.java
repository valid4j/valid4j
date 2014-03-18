package org.valid4j;

import org.hamcrest.*;

public abstract class BaseContractViolationPolicy implements ContractViolationPolicy {

	private final ErrorMessageBuilder msg;

	BaseContractViolationPolicy() {
		this(new ErrorMessageBuilder());
	}
	
	public BaseContractViolationPolicy(ErrorMessageBuilder errorMessageBuilder) {
		this.msg = errorMessageBuilder;
	}
	
	@Override
	public void require(Object o, Matcher<?> matcher) {
		if (!matcher.matches(o)) {
			require(false, withMismatchMessageOf(o, matcher));
		}
	}

	@Override
	public void ensure(Object o, Matcher<?> matcher) {
		if (!matcher.matches(o)) {
			ensure(false, withMismatchMessageOf(o, matcher));
		}
	}

	private String withMismatchMessageOf(Object actual, Matcher<?> matcher) {
		return msg.describeMismatchOf(actual, matcher).toString();
	}
	
	protected static String withMessage(String header, String msg) {
		final StringBuilder sb = new StringBuilder(header);
		if (msg != null) {
			sb.append(":");
			sb.append(msg);
		}
		return sb.toString();
	}

}
