package org.valid4j;

import org.hamcrest.Matcher;

/**
 * Policy for handling contract violations
 */
public interface ViolationPolicy {

  void handleViolation(String msg, Object... values);

  void handleViolation(Object o, Matcher<?> matcher);

}
