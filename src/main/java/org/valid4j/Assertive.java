package org.valid4j;

import org.hamcrest.Matcher;

/**
 * Entry point for the global assertive policy.
 * <p>
 * Violations indicate the presence of a programming error.
 * Never try to catch and recover from a programming error. Rather
 * a reasonable global approach would be to return a HTTP 500 response,
 * restart the application or similar.
 * </p>
 * Make use of hamcrest matchers to conveniently express conditions and get clear error messages.
 * Example:
 * <pre>
 * {@code
 *   require(i, greaterThan(0));
 * }
 * </pre>
 * <p>
 * All error messages are {@link java.lang.String#format format strings} as defined by {@link java.lang.String}.
 * </p>
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
  private static final Error DEFAULT_ERROR = null;

  private Assertive() {
    throw neverGetHereError("Prevent instantiation");
  }

  /**
   * Precondition that clients are required to fulfill.
   * Violations are considered to be programming errors,
   * on the clients part.
   *
   * @param condition the condition to check
   */
  public static void require(boolean condition) {
    getProvider().requirePolicy().check(condition, NULL_MESSAGE);
  }

  /**
   * Precondition that clients are required to fulfill.
   * Violations are considered to be programming errors,
   * on the clients part.
   *
   * @param <T>     type of object to check
   * @param o       the object to check
   * @param matcher the matcher that the given object must satisfy
   * @return the validated object
   */
  public static <T> T require(T o, Matcher<?> matcher) {
    getProvider().requirePolicy().check(o, matcher);
    return o;
  }

  /**
   * Precondition that clients are required to fulfill.
   * Violations are considered to be programming errors,
   * on the clients part.
   *
   * @param condition the condition to check
   * @param msg       message {@link java.lang.String#format format string}
   * @param values    values passed into the msg format string
   */
  public static void require(boolean condition, String msg, Object... values) {
    getProvider().requirePolicy().check(condition, msg, values);
  }

  /**
   * Postcondition that supplier are supposed to ensure.
   * Violations are considered to be a programming error,
   * on the suppliers part.
   *
   * @param condition the condition to check
   */
  public static void ensure(boolean condition) {
    getProvider().ensurePolicy().check(condition, NULL_MESSAGE);
  }

  /**
   * Postcondition that supplier are supposed to ensure.
   * Violations are considered to be programming errors,
   * on the suppliers part.
   *
   * @param <T>     type of object to check
   * @param o       the object to check
   * @param matcher the matcher that the given object must satisfy
   * @return        the validated object
   */
  public static <T> T ensure(T o, Matcher<?> matcher) {
    getProvider().ensurePolicy().check(o, matcher);
    return o;
  }

  /**
   * Postcondition that supplier are supposed to ensure.
   * Violations are considered to be programming errors,
   * on the suppliers part.
   *
   * @param condition the condition to check
   * @param msg       message {@link java.lang.String#format format string}
   * @param values    values passed into the msg format string
   */
  public static void ensure(boolean condition, String msg, Object... values) {
    getProvider().ensurePolicy().check(condition, msg, values);
  }

  /**
   * Unreachable code have been reached.
   * This is considered to be a programming error.
   */
  public static void neverGetHere() {
    getProvider().neverGetHerePolicy().neverGetHere(NO_CAUSE, NULL_MESSAGE);
  }

  /**
   * Unreachable code have been reached. This is
   * considered to be a programming error.
   *
   * @param msg descriptive message
   */
  public static void neverGetHere(String msg) {
    getProvider().neverGetHerePolicy().neverGetHere(NO_CAUSE, msg);
  }

  /**
   * Unreachable code have been reached. This is
   * considered to be a programming error.
   *
   * @param msg    message {@link java.lang.String#format format string}
   * @param values values passed into the msg format string
   */
  public static void neverGetHere(String msg, Object... values) {
    getProvider().neverGetHerePolicy().neverGetHere(NO_CAUSE, msg, values);
  }

  /**
   * Unexpected exception have been caught. This is considered
   * to be a programming error.
   *
   * @param t the throwable being unexpectedly caught.
   */
  public static void neverGetHere(Throwable t) {
    getProvider().neverGetHerePolicy().neverGetHere(t, NULL_MESSAGE);
  }

  /**
   * Unexpected exception have been caught. This is considered
   * to be a programming error.
   *
   * @param t      the throwable being unexpectedly caught.
   * @param msg    message {@link java.lang.String#format format string}
   * @param values values passed into the msg format string
   */
  public static void neverGetHere(Throwable t, String msg, Object... values) {
    getProvider().neverGetHerePolicy().neverGetHere(t, msg, values);
  }

  /**
   * Unreachable code have been reached.
   * This is considered to be a programming error.
   * @return an error
   */
  public static Error neverGetHereError() {
    getProvider().neverGetHerePolicy().neverGetHere(NO_CAUSE, NULL_MESSAGE);
    return DEFAULT_ERROR;
  }

  /**
   * Unreachable code have been reached. This is
   * considered to be a programming error.
   *
   * @param msg descriptive message
   * @return an error
   */
  public static Error neverGetHereError(String msg) {
    getProvider().neverGetHerePolicy().neverGetHere(NO_CAUSE, msg);
    return DEFAULT_ERROR;
  }

  /**
   * Unreachable code have been reached. This is
   * considered to be a programming error.
   *
   * @param msg    message {@link java.lang.String#format format string}
   * @param values values passed into the msg format string
   * @return an error
   */
  public static Error neverGetHereError(String msg, Object... values) {
    getProvider().neverGetHerePolicy().neverGetHere(NO_CAUSE, msg, values);
    return DEFAULT_ERROR;
  }

  /**
   * Unexpected exception have been caught. This is considered
   * to be a programming error.
   *
   * @param t the throwable being unexpectedly caught.
   * @return an error
   */
  public static Error neverGetHereError(Throwable t) {
    getProvider().neverGetHerePolicy().neverGetHere(t, NULL_MESSAGE);
    return DEFAULT_ERROR;
  }

  /**
   * Unexpected exception have been caught. This is considered
   * to be a programming error.
   *
   * @param t      the throwable being unexpectedly caught.
   * @param msg    message {@link java.lang.String#format format string}
   * @param values values passed into the msg format string
   * @return an error
   */
  public static Error neverGetHereError(Throwable t, String msg, Object... values) {
    getProvider().neverGetHerePolicy().neverGetHere(t, msg, values);
    return DEFAULT_ERROR;
  }

  private static AssertiveProvider getProvider() {
    return AssertiveInstance.getInstance();
  }

}