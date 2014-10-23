package org.valid4j;

import org.hamcrest.Matcher;

import static org.valid4j.Assertive.neverGetHere;

/**
 * Entry point for validation methods. 
 * 
 * Use these methods to throw recoverable exceptions if validation fails.
 * Recoverable exceptions are exceptions that a correct program can and 
 * should catch and recover from. (Recoverable exceptions may be checked
 * or unchecked.)
 */
public class Validation {

  private Validation() {
    neverGetHere("Prevent instantiation");
  }

  /**
   * Validate the boolean expression that is supposed to be true.
   * If not, a recoverable exception will be thrown that the client
   * is supposed to catch and recover from.
   *
   * @param condition the condition to validate. Supposed to be true.
   * @param exception the recoverable exception to be thrown if validation fails.
   * @throws X the recoverable exception, if validation fails
   */
  public static <X extends Exception> void validate(boolean condition, X exception) throws X {
    if (!condition) {
      throw exception;
    }
  }

  public static <X extends Exception> void validate(boolean condition, Class<X> exceptionClass) throws X {
    if (!condition) {
      throw ExceptionFactories.builder(exceptionClass).newInstance(null);
    }
  }

  /**
   * Validate the given object that is supposed to satisfy the matcher.
   * If not, a recoverable exception will be thrown that the client
   * is supposed to catch and recover from.
   *
   * @param o         the object to validate
   * @param matcher   the matcher that the object must satisfy
   * @param exception the recoverable exception to be thrown if validation fails.
   * @return the validated object, if valid
   * @throws X the recoverable exception, if validation fails
   */
  public static <T, X extends Exception> T validate(T o, Matcher<?> matcher, X exception) throws X {
    if (!matcher.matches(o)) {
      throw exception;
    }
    return o;
  }

  public static <T, X extends Exception> T validate(T o, Matcher<?> matcher, Class<X> exceptionClass) throws X {
    if (!matcher.matches(o)) {
      throw ExceptionFactories.builder(exceptionClass).newInstance(withMessage(o, matcher));
    }
    return o;
  }

  private static String withMessage(Object actual, Matcher<?> matcher) {
    return Message.withMismatchMessageOf(actual, matcher);
  }
}