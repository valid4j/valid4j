package org.valid4j.impl;

import org.valid4j.UnreachablePolicy;
import org.valid4j.errors.ContractViolation;
import org.valid4j.errors.NeverGetHereViolation;

import java.util.Queue;

import static org.valid4j.Message.describing;

/**
 * Policy for handling unreachable code by throwing an unrecoverable exception.
 */
public class NeverGetHerePolicy extends TrackingViolationPolicy implements UnreachablePolicy {

  public NeverGetHerePolicy(Queue<ContractViolation> violations) {
    super(violations);
  }

  @Override
  public void neverGetHere(Throwable cause, String msg, Object... values) {
    throw tracked(new NeverGetHereViolation(cause, describing(msg, values).toString()));
  }
}
