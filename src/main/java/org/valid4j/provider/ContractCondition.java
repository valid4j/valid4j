package org.valid4j.provider;

/**
 * Represents a contract condition that should always be satisfied.
 */
public interface ContractCondition {

  boolean isNotSatisfied();

  String message();

}