package org.valid4j.impl;

import org.hamcrest.Matcher;
import org.valid4j.AssertivePolicy;

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
