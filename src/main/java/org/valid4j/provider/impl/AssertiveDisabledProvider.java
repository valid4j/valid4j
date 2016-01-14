package org.valid4j.provider.impl;

import org.valid4j.provider.AssertiveProvider;
import org.valid4j.provider.CheckPolicy;
import org.valid4j.provider.UnreachablePolicy;

/**
 * An assertive policy provider that disables all checks and assertive handling
 */
@SuppressWarnings("unused")
public class AssertiveDisabledProvider implements AssertiveProvider {

  @Override
  public CheckPolicy requirePolicy() {
    return new NonCheckingPolicy();
  }

  @Override
  public CheckPolicy ensurePolicy() {
    return new NonCheckingPolicy();
  }

  @Override
  public UnreachablePolicy neverGetHerePolicy() {
    return new DoNothingPolicy();
  }

  private static class DoNothingPolicy implements UnreachablePolicy {
    @Override
    public void neverGetHere(Throwable t, Object messageBuilder) {
      // Do nothing
    }
  }
}
