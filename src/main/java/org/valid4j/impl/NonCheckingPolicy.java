package org.valid4j.impl;

import org.valid4j.CheckPolicy;
import org.valid4j.ContractCondition;

/**
 * An assertive policy that performs no checking at all.
 */
public class NonCheckingPolicy implements CheckPolicy {

  @Override
  public void check(final ContractCondition contractCondition) {
    // Do nothing
  }

}