package org.valid4j;

import org.hamcrest.Matcher;

import static org.valid4j.Message.describingMismatchOf;

/**
 * An implementation of a hamcrest matcher contract condition.
 */
class MatcherContractCondition implements ContractCondition {

  private final Object o;
  private final Matcher<?> matcher;

  private MatcherContractCondition(final Object o, final Matcher<?> matcher) {
    this.o = o;
    this.matcher = matcher;
  }

  public static ContractCondition matcherContract(final Object o, final Matcher<?> matcher) {
    return new MatcherContractCondition(o, matcher);
  }

  @Override
  public boolean isNotSatisfied() {
    return !matcher.matches(o);
  }

  @Override
  public String message() {
    return describingMismatchOf(o, matcher).toString();
  }

}