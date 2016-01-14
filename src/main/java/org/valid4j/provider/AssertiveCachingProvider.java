package org.valid4j.provider;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.valid4j.Validation.validate;

/**
 * Assertive policy provider that caches the policies of the delegate.
 */
class AssertiveCachingProvider implements AssertiveProvider {

  private final CheckPolicy requirePolicy;
  private final CheckPolicy ensurePolicy;
  private final UnreachablePolicy neverGetHerePolicy;

  public AssertiveCachingProvider(AssertiveProvider source) {
    this.requirePolicy = validate(source.requirePolicy(), notNullValue(),
        new NullPointerException("Missing require policy"));
    this.ensurePolicy = validate(source.ensurePolicy(), notNullValue(),
        new NullPointerException("Missing ensure policy"));
    this.neverGetHerePolicy = validate(source.neverGetHerePolicy(), notNullValue(),
        new NullPointerException("Missing never get here policy"));
  }

  public static AssertiveProvider cached(AssertiveProvider delegate) {
    return new AssertiveCachingProvider(delegate);
  }

  @Override
  public CheckPolicy requirePolicy() {
    return requirePolicy;
  }

  @Override
  public CheckPolicy ensurePolicy() {
    return ensurePolicy;
  }

  @Override
  public UnreachablePolicy neverGetHerePolicy() {
    return neverGetHerePolicy;
  }
}
