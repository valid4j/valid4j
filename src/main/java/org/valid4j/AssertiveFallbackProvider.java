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
    return defaultIfNull(delegate.requirePolicy(), fallback.requirePolicy());
  }

  @Override
  public AssertivePolicy ensurePolicy() {
    return defaultIfNull(delegate.ensurePolicy(), fallback.ensurePolicy());
  }

  @Override
  public UnreachablePolicy neverGetHerePolicy() {
    return defaultIfNull(delegate.neverGetHerePolicy(), fallback.neverGetHerePolicy());
  }

  private static <T> T defaultIfNull(T firstHand, T fallback) {
    return firstHand != null ? firstHand : fallback;
  }

}
