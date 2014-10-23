package org.valid4j;

import org.hamcrest.Matcher;
import org.valid4j.exceptions.NeverGetHereViolation;

import static org.valid4j.AssertiveCachingProvider.cached;
import static org.valid4j.Message.withFormattedMessage;

/**
 * Entry point for the global assertive policy.
 * <p/>
 * Violations indicate the presence of a programming error.
 * Never try to catch and recover from a programming error. Rather
 * a reasonable global approach would be to return a HTTP 500 response,
 * restart the application or similar.
 * <p/>
 * Make use of hamcrest matchers to conveniently express conditions and get clear error messages.
 * Example:
 * <pre>
 * {@code
 *   require(i, greaterThan(0));
 * }
 * </pre>
 * <p/>
 * All error messages are {@link java.lang.String#format format strings} as defined by {@link java.lang.String}.
 * <p/>
 * Example:
 * <pre>
 * {@code
 *   require(i > 0, "The value must be greater than zero: %d", i);
 * }
 * </pre>
 */
public class Assertive {

  public static final String ASSERTIVE_PROPERTY_NAME = AssertiveProvider.class.getName();
  public static final String DISABLED = "disabled";
  public static final String DEFAULT_PROVIDER = "org.valid4j.AssertiveDefaultProvider";
  public static final String DISABLED_PROVIDER = "org.valid4j.AssertiveDisabledProvider";

  private static final String NULL_MESSAGE = null;
  private static final Throwable NO_CAUSE = null;

  private static AssertiveProvider provider;

  /**
   * To customize the behavior of Assertive, specify a system property (org.valid4j.Assertive)
   * with the class name of the assertive policy provider.
   */
  static {
    init();
  }

  static void init() {
    try {
      provider = cached(createProvider(getProviderName()));
    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | ClassCastException e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  private static String getProviderName() {
    String providerName = System.getProperty(ASSERTIVE_PROPERTY_NAME, DEFAULT_PROVIDER);
    return DISABLED.equals(providerName) ? DISABLED_PROVIDER : providerName;
  }

  private static AssertiveProvider createProvider(String providerName)
      throws ClassNotFoundException, IllegalAccessException, InstantiationException, ClassCastException {
    Class c = Class.forName(providerName);
    AssertiveProvider newProvider = (AssertiveProvider) c.newInstance();
    return newProvider;
  }

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
    provider.requirePolicy().check(condition, NULL_MESSAGE);
  }

  /**
   * Precondition that clients are required to fulfill.
   * Violations are considered to be programming errors,
   * on the clients part.
   *
   * @param o       the object to check
   * @param matcher the matcher that the given object must satisfy
   * @return the validated object
   */
  public static <T> T require(T o, Matcher<?> matcher) {
    provider.requirePolicy().check(o, matcher);
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
    provider.requirePolicy().check(condition, msg, values);
  }

  /**
   * Postcondition that supplier are supposed to ensure.
   * Violations are considered to be a programming error,
   * on the suppliers part.
   *
   * @param condition the condition to check
   */
  public static void ensure(boolean condition) {
    provider.ensurePolicy().check(condition, NULL_MESSAGE);
  }

  /**
   * Postcondition that supplier are supposed to ensure.
   * Violations are considered to be programming errors,
   * on the suppliers part.
   *
   * @param o       the object to check
   * @param matcher the matcher that the given object must satisfy
   * @return        the validated object
   */
  public static <T> T ensure(T o, Matcher<?> matcher) {
    provider.ensurePolicy().check(o, matcher);
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
    provider.ensurePolicy().check(condition, msg, values);
  }

  /**
   * Unreachable code have been reached.
   * This is considered to be a programming error.
   */
  public static Error neverGetHere() {
    provider.neverGetHerePolicy().neverGetHere(NO_CAUSE, NULL_MESSAGE);
    return unreachableError(NO_CAUSE, NULL_MESSAGE);
  }

  /**
   * Unreachable code have been reached. This is
   * considered to be a programming error.
   *
   * @param msg descriptive message
   */
  public static Error neverGetHere(String msg) {
    provider.neverGetHerePolicy().neverGetHere(NO_CAUSE, msg);
    return unreachableError(NO_CAUSE, msg);
  }

  /**
   * Unreachable code have been reached. This is
   * considered to be a programming error.
   *
   * @param msg    message {@link java.lang.String#format format string}
   * @param values values passed into the msg format string
   */
  public static Error neverGetHere(String msg, Object... values) {
    provider.neverGetHerePolicy().neverGetHere(NO_CAUSE, msg, values);
    return unreachableError(NO_CAUSE, msg, values);
  }

  /**
   * Unexpected exception have been caught. This is considered
   * to be a programming error.
   *
   * @param t the throwable being unexpectedly caught.
   */
  public static Error neverGetHere(Throwable t) {
    provider.neverGetHerePolicy().neverGetHere(t, NULL_MESSAGE);
    return unreachableError(t, NULL_MESSAGE);
  }

  /**
   * Unexpected exception have been caught. This is considered
   * to be a programming error.
   *
   * @param t      the throwable being unexpectedly caught.
   * @param msg    message {@link java.lang.String#format format string}
   * @param values values passed into the msg format string
   */
  public static Error neverGetHere(Throwable t, String msg, Object... values) {
    provider.neverGetHerePolicy().neverGetHere(t, msg, values);
    return unreachableError(t, msg, values);
  }

  // This is indeed meant as an unreachable error, and is the fallback error returned
  // if Assertive have been disabled. If clients have used the invocation style of
  // "throw neverGetHere(...)", this is the error that will be thrown in that case.
  private static Error unreachableError(Throwable cause, String msg, Object... values) {
    return new NeverGetHereViolation(cause, withFormattedMessage(msg, values));
  }
}
