package org.valid4j.provider.impl;

import org.valid4j.provider.AssertiveProvider;
import org.valid4j.provider.CheckPolicy;
import org.valid4j.provider.UnreachablePolicy;
import org.valid4j.provider.errors.ContractViolation;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Provider of default assertive policies for valid4j.
 */
@SuppressWarnings("unused")
public class AssertiveDefaultProvider implements AssertiveProvider {

  private static final int MAX_CAPACITY = 10;
  private static Queue<ContractViolation> violations;

  public AssertiveDefaultProvider() {
    violations = new ArrayBlockingQueue<ContractViolation>(MAX_CAPACITY);
  }

  /**
   * Get the first contract violations that have occurred in current process.
   * @return the (up to 10) first contract violations in the order of occurrence
   */
  public static List<ContractViolation> getFirstTrackedViolations() {
    return new ArrayList<>(violations);
  }

  @Override
  public CheckPolicy requirePolicy() {
    return new CheckingPolicy(new RequireViolationPolicy(violations));
  }

  @Override
  public CheckPolicy ensurePolicy() {
    return new CheckingPolicy(new EnsureViolationPolicy(violations));
  }

  @Override
  public UnreachablePolicy neverGetHerePolicy() {
    return new NeverGetHerePolicy(violations);
  }
}
