package org.valid4j.provider.impl;

import org.valid4j.provider.errors.ContractViolation;
import org.valid4j.provider.errors.RequireViolation;

import java.util.Queue;

/**
 * Policy for handling pre-conditions by throwing a contract violation.
 */
public class RequireViolationPolicy extends TrackingViolationPolicy implements ViolationPolicy {

  public RequireViolationPolicy(Queue<ContractViolation> violations) {
    super(violations);
  }

  @Override
  public void handleViolation(String message) {
    throw tracked(new RequireViolation(message));
  }
}
