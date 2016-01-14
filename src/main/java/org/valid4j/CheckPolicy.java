package org.valid4j;

/**
 * Policy for checking conditions.
 */
public interface CheckPolicy {

	void check(ContractCondition contractCondition);

}
