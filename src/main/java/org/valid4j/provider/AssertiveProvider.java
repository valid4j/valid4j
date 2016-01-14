package org.valid4j.provider;

/**
 * Provider of assertive policies.
 */
public interface AssertiveProvider {
	
	/**
	 * @return the policy to use for checking preconditions
	 */
	CheckPolicy requirePolicy();
	
	/**
	 * @return the policy to use for checking postconditions
	 */
	CheckPolicy ensurePolicy();
	
	/**
	 * @return the policy to use for reaching presumably unreachable code.
	 */
	UnreachablePolicy neverGetHerePolicy();
	
}
