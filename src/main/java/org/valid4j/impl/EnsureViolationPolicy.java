package org.valid4j.impl;

import org.valid4j.ViolationPolicy;
import org.valid4j.exceptions.ContractViolation;
import org.valid4j.exceptions.EnsureViolation;

import java.util.Queue;

/**
 * Policy for handling post-conditions by throwing a contract violation.
 */
public class EnsureViolationPolicy extends TrackingViolationPolicy implements ViolationPolicy {

  public EnsureViolationPolicy(Queue<ContractViolation> violations) {
    super(violations);
  }

  @Override
  public void handleViolation(String message) {
    throw tracked(new EnsureViolation(message));
  }
}
