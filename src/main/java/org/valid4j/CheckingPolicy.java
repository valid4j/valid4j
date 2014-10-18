package org.valid4j;

import org.hamcrest.Matcher;

/**
 * An implementation of the assertive policy that checks the given conditions
 * for violations.
 */
public class CheckingPolicy implements AssertivePolicy {

  private final ViolationPolicy violationPolicy;

  public CheckingPolicy(ViolationPolicy violationPolicy) {
    this.violationPolicy = violationPolicy;
  }
  
  @Override
  public void check(boolean condition, String msg, Object... values) {
    if (!condition) {
      violationPolicy.handleViolation(msg, values);
    }
  }

  @Override
  public void check(Object o, Matcher<?> matcher) {
    if (!matcher.matches(o)) {
      violationPolicy.handleViolation(o, matcher);
    }
  }

}
