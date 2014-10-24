package org.valid4j.impl;

import org.hamcrest.Matcher;
import org.valid4j.ViolationPolicy;
import org.valid4j.exceptions.ContractViolation;
import org.valid4j.exceptions.RequireViolation;

import java.util.Queue;

import static org.valid4j.Message.withFormattedMessage;
import static org.valid4j.Message.withMismatchMessageOf;

/**
 * Policy for handling pre-conditions by throwing a contract violation.
 */
public class RequireViolationPolicy implements ViolationPolicy {

  private final Queue<ContractViolation> violations;

  public RequireViolationPolicy(Queue<ContractViolation> violations) {
    this.violations = violations;
  }

  @Override
  public void handleViolation(String msg, Object... values) {
    throw tracked(new RequireViolation(withFormattedMessage(msg, values)));
  }

  @Override
  public void handleViolation(Object o, Matcher<?> matcher) {
    throw tracked(new RequireViolation(withMismatchMessageOf(o, matcher)));
  }

  private RequireViolation tracked(RequireViolation requireViolation) {
    violations.offer(requireViolation);
    return requireViolation;
  }
}
