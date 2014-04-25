package org.valid4j;

import static org.valid4j.Message.withFormattedMessage;
import static org.valid4j.Message.withMismatchMessageOf;

import org.hamcrest.Matcher;
import org.valid4j.exceptions.EnsureViolation;

/**
 * Policy for handling post-conditions by throwing a contract violation.
 */
public class EnsureViolationPolicy implements ViolationPolicy {

  @Override
  public void handleViolation(String msg, Object... values) {
    throw new EnsureViolation(withFormattedMessage(msg, values));
  }

  @Override
  public void handleViolation(Object o, Matcher<?> matcher) {
    throw new EnsureViolation(withMismatchMessageOf(o, matcher));
  }

}
