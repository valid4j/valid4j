package org.valid4j;

import org.hamcrest.*;

public class Validator<T extends RuntimeException> {
	
	private final ErrorMessageBuilder error;
	private final ExceptionFactory<T> exception;
	
	public static Validator<IllegalArgumentException> validator() {
		return validator(IllegalArgumentException.class);
	}
	
	public static <X extends RuntimeException> Validator<X> validator(Class<X> validationException) {
		return new Validator<X>(ExceptionFactories.builder(validationException));
	}
	
	public Validator(ExceptionFactory<T> exceptionBuilder) {
		this(exceptionBuilder, new ErrorMessageBuilder());
	}
		
	public Validator(ExceptionFactory<T> exceptionFactory, ErrorMessageBuilder errorMsgBuilder) {
		this.exception = exceptionFactory;
		this.error = errorMsgBuilder;
	}

	public void argument(boolean condition) throws T {
		if (!condition) {
			throw newValidationException("the validated expression is false");
		}
	}

	public void argument(boolean condition, String formatString, Object... params) {
		if (!condition) {
			throw newValidationException(withMessage(formatString, params));
		}
	}

	public <V> V argument(V actual, Matcher<?> matcher) {
		if (!matcher.matches(actual)) {
			throw newValidationException(withMessage(actual, matcher));
		}
		return actual;
	}

	private T newValidationException(final String msg) {
		return exception.newInstance(msg);
	}

	private String withMessage(String formatString, Object... params) {
		return error.withMessage(formatString, params);
	}

	private String withMessage(Object actual, Matcher<?> matcher) {
		return error.withMismatchOf(actual, matcher);
	}

}
