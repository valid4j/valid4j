package org.valid4j.impl;

import org.hamcrest.Matcher;
import org.valid4j.CheckPolicy;

import static org.valid4j.Message.describing;
import static org.valid4j.Message.describingMismatchOf;

/**
 * An implementation of the assertive policy that checks the given conditions
 * for violations.
 */
public class CheckingPolicy implements CheckPolicy {

  private final ViolationPolicy violationPolicy;

  public CheckingPolicy(ViolationPolicy violationPolicy) {
    this.violationPolicy = violationPolicy;
  }
  
  @Override
  public void check(boolean condition, String msg, Object... values) {
    if (!condition) {
      String message = describing(msg, values).toString();
      violationPolicy.handleViolation(message);
    }
  }

  @Override
  public void check(Object o, Matcher<?> matcher) {
    if (!matcher.matches(o)) {
      String message = describingMismatchOf(o, matcher).toString();
      violationPolicy.handleViolation(message);
    }
  }

}
