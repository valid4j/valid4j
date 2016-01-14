package org.valid4j;

/**
 * An implementation of a plain boolean contract condition.
 */
class BooleanContractCondition implements ContractCondition {

  private final boolean isSatisfied;
  private final Object messageBuilder;

  private BooleanContractCondition(final boolean isSatisfied, final Object messageBuilder) {
    this.messageBuilder = (messageBuilder == null ? "" : messageBuilder);
    this.isSatisfied = isSatisfied;
  }

  public static ContractCondition booleanContract(final boolean condition, final Object messageBuilder) {
    return new BooleanContractCondition(condition, messageBuilder);
  }

  @Override
  public boolean isNotSatisfied() {
    return !isSatisfied;
  }

  @Override
  public String message() {
    return messageBuilder.toString();
  }

}