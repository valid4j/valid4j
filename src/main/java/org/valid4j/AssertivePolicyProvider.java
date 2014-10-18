package org.valid4j;

/**
 * Provider of assertive policies.
 */
public interface AssertivePolicyProvider {
	
	/**
	 * @return the policy to use for checking preconditions
	 */
	AssertivePolicy requirePolicy();
	
	/**
	 * @return the policy to use for checking postconditions
	 */
	AssertivePolicy ensurePolicy();
	
	/**
	 * @return the policy to use for reaching presumably unreachable code.
	 */
	UnreachablePolicy neverGetHerePolicy();
	
}
