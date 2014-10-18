package org.valid4j.fixture;

import org.valid4j.AssertivePolicy;
import org.valid4j.AssertivePolicyProvider;
import org.valid4j.UnreachablePolicy;

/**
 * Protected assertive policy provider. Used to demonstrate that initialization of a protected
 * assertive policy provider, will result in initialization error.
 */
class AssertiveProtectedProvider implements AssertivePolicyProvider {

  @Override
  public AssertivePolicy requirePolicy() {
    return null;
  }

  @Override
  public AssertivePolicy ensurePolicy() {
    return null;
  }

  @Override
  public UnreachablePolicy neverGetHerePolicy() {
    return null;
  }
}
