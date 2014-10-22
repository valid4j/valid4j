package org.valid4j.fixture;

import org.valid4j.AssertivePolicy;
import org.valid4j.AssertiveProvider;
import org.valid4j.UnreachablePolicy;

/**
 * Customized assertive policy provider in order to test customized behavior.
 */
public class AssertiveMockProvider implements AssertiveProvider {

  public static AssertivePolicy requirePolicy = null;
  public static AssertivePolicy ensurePolicy = null;
  public static UnreachablePolicy neverGetHerePolicy = null;

  @Override
  public AssertivePolicy requirePolicy() {
    return requirePolicy;
  }

  @Override
  public AssertivePolicy ensurePolicy() {
    return ensurePolicy;
  }

  @Override
  public UnreachablePolicy neverGetHerePolicy() {
    return neverGetHerePolicy;
  }
}
