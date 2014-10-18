package org.valid4j;

/**
 * An assertive policy provider that disables all checks and assertive handling
 */
public class AssertiveDisabledProvider implements AssertivePolicyProvider {

  public static AssertivePolicyProvider disabledProvider() {
    return new AssertiveDisabledProvider();
  }

  @Override
  public AssertivePolicy requirePolicy() {
    return new NonCheckingPolicy();
  }

  @Override
  public AssertivePolicy ensurePolicy() {
    return new NonCheckingPolicy();
  }

  @Override
  public UnreachablePolicy neverGetHerePolicy() {
    return new DoNothingPolicy();
  }

  private static class DoNothingPolicy implements UnreachablePolicy {
    @Override
    public void neverGetHere(Throwable t, String msg, Object... values) {
      // Do nothing
    }
  }
}
