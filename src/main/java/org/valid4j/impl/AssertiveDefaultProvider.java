package org.valid4j.impl;

import org.valid4j.AssertivePolicy;
import org.valid4j.AssertiveProvider;
import org.valid4j.UnreachablePolicy;

/**
 * Provider of default assertive policies for valid4j.
 */
@SuppressWarnings("unused")
public class AssertiveDefaultProvider implements AssertiveProvider {

  @Override
  public AssertivePolicy requirePolicy() {
    return new CheckingPolicy(new RequireViolationPolicy());
  }

  @Override
  public AssertivePolicy ensurePolicy() {
    return new CheckingPolicy(new EnsureViolationPolicy());
  }

  @Override
  public UnreachablePolicy neverGetHerePolicy() {
    return new NeverGetHerePolicy();
  }
}
