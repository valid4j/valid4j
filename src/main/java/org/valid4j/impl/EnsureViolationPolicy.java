package org.valid4j.impl;

import org.hamcrest.Matcher;
import org.valid4j.ViolationPolicy;
import org.valid4j.exceptions.ContractViolation;
import org.valid4j.exceptions.EnsureViolation;

import java.util.Queue;

import static org.valid4j.Message.withFormattedMessage;
import static org.valid4j.Message.withMismatchMessageOf;

/**
 * Policy for handling post-conditions by throwing a contract violation.
 */
public class EnsureViolationPolicy implements ViolationPolicy {

  private final Queue<ContractViolation> violations;

  public EnsureViolationPolicy(Queue<ContractViolation> violations) {
    this.violations = violations;
  }

  @Override
  public void handleViolation(String msg, Object... values) {
    throw tracked(new EnsureViolation(withFormattedMessage(msg, values)));
  }

  @Override
  public void handleViolation(Object o, Matcher<?> matcher) {
    throw tracked(new EnsureViolation(withMismatchMessageOf(o, matcher)));
  }

  private EnsureViolation tracked(EnsureViolation ensureViolation) {
    violations.offer(ensureViolation);
    return ensureViolation;
  }
}
