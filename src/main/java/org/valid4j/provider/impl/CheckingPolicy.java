package org.valid4j.provider.impl;

import org.valid4j.provider.CheckPolicy;
import org.valid4j.provider.ContractCondition;

/**
 * An implementation of the assertive policy that checks the given conditions
 * for violations.
 */
public class CheckingPolicy implements CheckPolicy {

  private final ViolationPolicy violationPolicy;

  public CheckingPolicy(final ViolationPolicy violationPolicy) {
    this.violationPolicy = violationPolicy;
  }
  
  @Override
  public void check(final ContractCondition condition) {
    if (condition.isNotSatisfied()) {
      violationPolicy.handleViolation(condition.message());
    }
  }

}