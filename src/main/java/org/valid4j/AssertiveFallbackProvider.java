package org.valid4j;

/**
 * Assertive policy provider that returns a fallback provider if the first-hand provider
 * doesn't provide a policy.
 */
public class AssertiveFallbackProvider implements AssertiveProvider {

  private final AssertiveProvider delegate;
  private final AssertiveProvider fallback;

  public static AssertiveProvider fallbackIfNotSuppliedBy(
      AssertiveProvider delegate,
      AssertiveProvider fallback) {
    return new AssertiveFallbackProvider(delegate, fallback);
  }

  public AssertiveFallbackProvider(AssertiveProvider delegate, AssertiveProvider fallback) {
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
