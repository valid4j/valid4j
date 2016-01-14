package org.valid4j.provider;

/**
 * Policy for checking conditions.
 */
public interface CheckPolicy {

	void check(ContractCondition contractCondition);

}
