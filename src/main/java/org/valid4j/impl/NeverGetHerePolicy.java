package org.valid4j.impl;

import org.valid4j.UnreachablePolicy;
import org.valid4j.exceptions.ContractViolation;
import org.valid4j.exceptions.NeverGetHereViolation;

import java.util.Queue;

import static org.valid4j.Message.withFormattedMessage;

/**
 * Policy for handling unreachable code by throwing an unrecoverable exception.
 */
public class NeverGetHerePolicy implements UnreachablePolicy {

  private final Queue<ContractViolation> violations;

  public NeverGetHerePolicy(Queue<ContractViolation> violations) {
    this.violations = violations;
  }

  @Override
  public void neverGetHere(Throwable cause, String msg, Object... values) {
    throw tracked(new NeverGetHereViolation(cause, withFormattedMessage(msg, values)));
  }

  private NeverGetHereViolation tracked(NeverGetHereViolation neverGetHereViolation) {
    violations.offer(neverGetHereViolation);
    return neverGetHereViolation;
  }
}
