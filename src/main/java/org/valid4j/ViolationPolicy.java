package org.valid4j;

/**
 * Policy for handling contract violations
 */
public interface ViolationPolicy {

  void handleViolation(String message);

}
