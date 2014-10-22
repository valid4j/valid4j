package org.valid4j;

/**
 * Assertive policy provider that caches the policies of the delegate.
 */
public class AssertiveCachingProvider implements AssertiveProvider {

  private final AssertivePolicy requirePolicy;
  private final AssertivePolicy ensurePolicy;
  private final UnreachablePolicy neverGetHerePolicy;

  public static AssertiveProvider cached(AssertiveProvider delegate) {
    return new AssertiveCachingProvider(delegate);
  }

  public AssertiveCachingProvider(AssertiveProvider delegate) {
    this.requirePolicy = delegate.requirePolicy();
    this.ensurePolicy = delegate.ensurePolicy();
    this.neverGetHerePolicy = delegate.neverGetHerePolicy();
  }

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
