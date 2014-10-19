package org.valid4j;

/**
 * Assertive policy provider that returns a fallback provider if the first-hand provider
 * doesn't provide a policy.
 */
public class AssertiveFallbackProvider implements AssertivePolicyProvider {

  private final AssertivePolicyProvider delegate;
  private final AssertivePolicyProvider fallback;

  public static AssertivePolicyProvider fallbackIfNotSuppliedBy(
      AssertivePolicyProvider delegate,
      AssertivePolicyProvider fallback) {
    return new AssertiveFallbackProvider(delegate, fallback);
  }

  public AssertiveFallbackProvider(AssertivePolicyProvider delegate, AssertivePolicyProvider fallback) {
    this.delegate = delegate;
    this.fallback = fallback;
  }

  @Override
  public AssertivePolicy requirePolicy() {
    AssertivePolicy firstHand = delegate.requirePolicy();
    return firstHand != null ? firstHand : fallback.requirePolicy();
  }

  @Override
  public AssertivePolicy ensurePolicy() {
    AssertivePolicy firstHand = delegate.ensurePolicy();
    return firstHand != null ? firstHand : fallback.ensurePolicy();
  }

  @Override
  public UnreachablePolicy neverGetHerePolicy() {
    UnreachablePolicy firstHand = delegate.neverGetHerePolicy();
    return firstHand != null ? firstHand : fallback.neverGetHerePolicy();
  }

}
