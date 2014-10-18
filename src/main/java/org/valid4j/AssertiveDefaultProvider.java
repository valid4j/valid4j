package org.valid4j;

/**
 * Provider of default assertive policies for valid4j.
 */
public class AssertiveDefaultProvider implements AssertivePolicyProvider {

  public static AssertivePolicyProvider defaultProvider() {
    return new AssertiveDefaultProvider();
  }

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
