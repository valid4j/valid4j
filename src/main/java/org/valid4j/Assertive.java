package org.valid4j;

import org.hamcrest.*;

/**
 * Entry point for the global assertive policy.
 * <p>
 * Violations indicate the presence of a programming error. 
 * Never try to catch and recover from a programming error. Rather
 * a reasonable global approach would be to return a HTTP 500 response, 
 * restart the application or similar.
 * <p>
 * All error messages are {@link java.lang.String#format format strings} as defined by {@link java.lang.String}.
 * <p>
 * Example:
 * <pre>
 * {@code
 *   require(i > 0, "The value must be greater than zero: %d", i);
 * }
 * </pre>
 */
public class Assertive {
	
	private static final String NULL_MESSAGE = null;
	private static final Throwable NO_CAUSE = null;
	
	private static AssertivePolicy requirePolicy = new CheckAssertivePolicy(new RequireViolationPolicy());
	private static AssertivePolicy ensurePolicy = new CheckAssertivePolicy(new EnsureViolationPolicy());
	private static UnreachablePolicy neverGetHerePolicy = new NeverGetHerePolicy();
	
	private Assertive() {
	  neverGetHere("Prevent instantiation");
	}
	
	/**
	 * Precondition that clients are required to fulfill.
	 * Violations are considered to be programming errors, 
	 * on the clients part.
	 * 
	 * @param condition the condition to check
	 */
	public static void require(boolean condition) {
	  requirePolicy.check(condition, NULL_MESSAGE);
	}
	
	/**
   * Precondition that clients are required to fulfill.
   * Violations are considered to be programming errors, 
   * on the clients part.
	 * 
	 * @param o        the object to check
	 * @param matcher  the matcher that the given object must satisfy
	 * @return         the validated object
	 */
	public static <T> T require(T o, Matcher<?> matcher) {
	  requirePolicy.check(o, matcher);
		return o;
	}
	
	/**
	 * Precondition that clients are required to fulfill.
   * Violations are considered to be programming errors, 
   * on the clients part.
	 * 
	 * @param condition  the condition to check
	 * @param msg        message {@link java.lang.String#format format string}
	 * @param values     values passed into the msg format string
	 */
	public static void require(boolean condition, String msg, Object... values) {
	  requirePolicy.check(condition, msg, values);
	}

	/**
	 * Postcondition that supplier are supposed to ensure.
	 * Violations are considered to be a programming error,
	 * on the suppliers part.
	 * 
	 * @param condition the condition to check
	 */
	public static void ensure(boolean condition) {
	  ensurePolicy.check(condition, NULL_MESSAGE);
	}

  /**
   * Postcondition that supplier are supposed to ensure.
   * Violations are considered to be programming errors, 
   * on the suppliers part.
   * 
   * @param o        the object to check
   * @param matcher  the matcher that the given object must satisfy
   * @return         the validated object
   */
	public static <T> T ensure(T o, Matcher<?> matcher) {
	  ensurePolicy.check(o, matcher);
		return o;
	}

	/**
   * Postcondition that supplier are supposed to ensure.
   * Violations are considered to be programming errors, 
   * on the suppliers part.
   * 
   * @param condition  the condition to check
   * @param msg        message {@link java.lang.String#format format string}
   * @param values     values passed into the msg format string
	 */
	public static void ensure(boolean condition, String msg, Object... values) {
	  ensurePolicy.check(condition, msg, values);
	}

	/**
	 * Unreachable code have been reached. 
	 * This is considered to be a programming error.
	 */
	public static void neverGetHere() {
	  neverGetHerePolicy.neverGetHere(NO_CAUSE, NULL_MESSAGE);
	}

	/**
	 * Unreachable code have been reached. This is 
	 * considered to be a programming error.
	 * 
	 * @param msg  descriptive message
	 */
	public static void neverGetHere(String msg) {
	  neverGetHerePolicy.neverGetHere(NO_CAUSE, msg);
	}

	/**
	 * Unreachable code have been reached. This is 
	 * considered to be a programming error.
	 * 
   * @param msg        message {@link java.lang.String#format format string}
   * @param values     values passed into the msg format string
	 */
	public static void neverGetHere(String msg, Object... values) {
	  neverGetHerePolicy.neverGetHere(NO_CAUSE, msg, values);
	}
	
	/**
	 * Unexpected exception have been caught. This is considered
	 * to be a programming error.
	 * 
	 * @param t  the throwable being unexpectedly caught.
	 */
	public static void neverGetHere(Throwable t) {
	  neverGetHerePolicy.neverGetHere(t, NULL_MESSAGE);
	}

	/**
   * Unexpected exception have been caught. This is considered
   * to be a programming error.
   * 
   * @param t       the throwable being unexpectedly caught.
   * @param msg     message {@link java.lang.String#format format string}
   * @param values  values passed into the msg format string
	 */
	public static void neverGetHere(Throwable t, String msg, Object... values) {
	  neverGetHerePolicy.neverGetHere(t, msg, values);
	}

}
