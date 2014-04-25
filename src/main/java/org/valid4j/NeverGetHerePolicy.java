package org.valid4j;

import static org.valid4j.Message.withFormattedMessage;

import org.valid4j.exceptions.NeverGetHereViolation;

/**
 * Policy for handling unreachable code by throwing an unrecoverable exception.
 */
public class NeverGetHerePolicy implements UnreachablePolicy {

  @Override
  public void neverGetHere(Throwable cause, String msg, Object... values) {
    throw new NeverGetHereViolation(cause, withFormattedMessage(msg, values));
  }

}
