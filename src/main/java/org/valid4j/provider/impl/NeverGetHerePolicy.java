package org.valid4j.provider.impl;

import org.valid4j.provider.UnreachablePolicy;
import org.valid4j.provider.errors.ContractViolation;
import org.valid4j.provider.errors.NeverGetHereViolation;

import java.util.Queue;

/**
 * Policy for handling unreachable code by throwing an unrecoverable exception.
 */
public class NeverGetHerePolicy extends TrackingViolationPolicy implements UnreachablePolicy {

  public NeverGetHerePolicy(Queue<ContractViolation> violations) {
    super(violations);
  }

  @Override
  public void neverGetHere(Throwable cause, Object messageBuilder) {
    throw tracked(new NeverGetHereViolation(cause, messageBuilder == null ? null : messageBuilder.toString()));
  }

}