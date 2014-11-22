package org.valid4j.impl;

/**
 * Policy for handling contract violations
 */
public interface ViolationPolicy {

  void handleViolation(String message);

}
