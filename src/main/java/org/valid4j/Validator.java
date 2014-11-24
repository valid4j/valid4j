package org.valid4j;

import org.hamcrest.Matcher;

public class Validator<T extends Exception> {
	
	private final ExceptionFactory<T> exception;
	
	public static <X extends Exception> Validator<X> validator(Class<X> validationException) {
		return new Validator<X>(ExceptionFactories.exception(validationException));
	}
		
	public Validator(ExceptionFactory<T> exceptionFactory) {
		this.exception = exceptionFactory;
	}

	public void validate(boolean condition) throws T {
		if (!condition) {
			throw newRecoverableException("the validated expression is false");
		}
	}

	public void validate(boolean condition, String formatString, Object... params) throws T {
		if (!condition) {
			throw newRecoverableException(withMessage(formatString, params));
		}
	}

	public <V> V validate(V actual, Matcher<?> matcher) throws T {
		if (!matcher.matches(actual)) {
			throw newRecoverableException(withMessage(actual, matcher));
		}
		return actual;
	}

	private T newRecoverableException(final String msg) {
		return exception.newInstance(msg);
	}

	private String withMessage(String formatString, Object... values) {
		return Message.withFormattedMessage(formatString, values);
	}

	private String withMessage(Object actual, Matcher<?> matcher) {
		return Message.withMismatchMessageOf(actual, matcher);
	}

}
