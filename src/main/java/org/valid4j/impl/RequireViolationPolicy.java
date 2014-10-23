package org.valid4j.impl;

import org.hamcrest.Matcher;
import org.valid4j.ViolationPolicy;
import org.valid4j.exceptions.RequireViolation;

import static org.valid4j.Message.withFormattedMessage;
import static org.valid4j.Message.withMismatchMessageOf;

/**
 * Policy for handling pre-conditions by throwing a contract violation.
 */
public class RequireViolationPolicy implements ViolationPolicy {

  @Override
  public void handleViolation(String msg, Object... values) {
      throw new RequireViolation(withFormattedMessage(msg, values));
  }

  @Override
  public void handleViolation(Object o, Matcher<?> matcher) {
    throw new RequireViolation(withMismatchMessageOf(o, matcher));
  }

}
