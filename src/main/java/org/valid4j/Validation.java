package org.valid4j;

import org.hamcrest.Matcher;

import static org.valid4j.Assertive.neverGetHere;
import static org.valid4j.ExceptionFactories.exception;
import static org.valid4j.Message.describingMismatchOf;

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

  public static <X extends Exception> ExceptionFactory<X> otherwiseThrowing(Class<X> exceptionClass) {
    return exception(exceptionClass);
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

  /**
   * Validate the boolean expression that is supposed to be true.
   * If not, a recoverable exception will be thrown that the client
   * is supposed to catch and recover from.
   *
   * @param condition the condition to validate. Supposed to be true.
   * @param exceptionClass the class of recoverable exception to be thrown if validation fails.
   * @throws X the recoverable exception, if validation fails
   */
  public static <X extends Exception> void validate(boolean condition, Class<X> exceptionClass) throws X {
    if (!condition) {
      throw exception(exceptionClass).newInstance(null);
    }
  }

  /**
   * Validate the boolean expression that is supposed to be true.
   * If not, a recoverable exception will be thrown that the client
   * is supposed to catch and recover from.
   *
   * @param condition the condition to validate. Supposed to be true.
   * @param factory the factory of recoverable exception to be thrown if validation fails.
   * @throws X the recoverable exception, if validation fails
   */
  public static <X extends Exception> void validate(boolean condition, ExceptionFactory<X> factory) throws X {
    if (!condition) {
      throw factory.newInstance(null);
    }
  }

  /**
   * Validate the given object that is supposed to satisfy the matcher.
   * If not, a recoverable exception will be thrown that the client
   * is supposed to catch and recover from.
   *
   * @param o the object to validate
   * @param matcher the matcher that the object must satisfy
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

  /**
   * Validate the given object that is supposed to satisfy the matcher.
   * If not, a recoverable exception will be thrown that the client
   * is supposed to catch and recover from.
   *
   * @param o the object to validate
   * @param matcher the matcher that the object must satisfy
   * @param exceptionClass the class of recoverable exception to be thrown if validation fails.
   * @return the validated object, if valid
   * @throws X the recoverable exception, if validation fails
   */
  public static <T, X extends Exception> T validate(T o, Matcher<?> matcher, Class<X> exceptionClass) throws X {
    if (!matcher.matches(o)) {
      throw exception(exceptionClass).newInstance(describingMismatchOf(o, matcher).toString());
    }
    return o;
  }

  /**
   * Validate the given object that is supposed to satisfy the matcher.
   * If not, a recoverable exception will be thrown that the client
   * is supposed to catch and recover from.
   *
   * @param o the object to validate
   * @param matcher the matcher that the object must satisfy
   * @param factory the factory of recoverable exception to be thrown if validation fails.
   * @return the validated object, if valid
   * @throws X the recoverable exception, if validation fails
   */
  public static <T, X extends Exception> T validate(T o, Matcher<?> matcher, ExceptionFactory<X> factory) throws X {
    if (!matcher.matches(o)) {
      throw factory.newInstance(describingMismatchOf(o, matcher).toString());
    }
    return o;
  }
}