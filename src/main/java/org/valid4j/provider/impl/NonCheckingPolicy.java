package org.valid4j.provider.impl;

import org.valid4j.provider.CheckPolicy;
import org.valid4j.provider.ContractCondition;

/**
 * An assertive policy that performs no checking at all.
 */
public class NonCheckingPolicy implements CheckPolicy {

  @Override
  public void check(final ContractCondition contractCondition) {
    // Do nothing
  }

}