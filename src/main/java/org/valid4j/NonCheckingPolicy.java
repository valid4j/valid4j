package org.valid4j;

import org.hamcrest.Matcher;

/**
 * An assertive policy that performs no checking at all.
 */
public class NonCheckingPolicy implements AssertivePolicy {

  @Override
  public void check(boolean condition, String msg, Object... values) {
    // Do nothing
  }

  @Override
  public void check(Object o, Matcher<?> matcher) {
    // Do nothing
  }
}
