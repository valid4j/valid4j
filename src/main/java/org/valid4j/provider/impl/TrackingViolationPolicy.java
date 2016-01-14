package org.valid4j.provider.impl;

import org.valid4j.provider.errors.ContractViolation;

import java.util.Queue;

/**
 * A violation policy that tracks thrown contract violations
 */
public class TrackingViolationPolicy {

  private final Queue<ContractViolation> violations;

  public TrackingViolationPolicy(Queue<ContractViolation> violations) {
    this.violations = violations;
  }

  protected ContractViolation tracked(ContractViolation violation) {
    violations.offer(violation);
    return violation;
  }
}
