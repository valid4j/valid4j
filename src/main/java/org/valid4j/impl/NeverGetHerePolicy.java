package org.valid4j.impl;

import org.valid4j.UnreachablePolicy;
import org.valid4j.exceptions.NeverGetHereViolation;

import static org.valid4j.Message.withFormattedMessage;

/**
 * Policy for handling unreachable code by throwing an unrecoverable exception.
 */
public class NeverGetHerePolicy implements UnreachablePolicy {

  @Override
  public void neverGetHere(Throwable cause, String msg, Object... values) {
    throw new NeverGetHereViolation(cause, withFormattedMessage(msg, values));
  }

}
