package org.valid4j;

import org.hamcrest.*;

/**
 * Entry point for the global Design by Contract policy.
 * See http://en.wikipedia.org/wiki/Design_by_contract
 * 
 * @author Patrik Helsing
 *
 */
public class DesignByContract {
	
	private static ContractViolationPolicy policy = new DefaultContractViolationPolicy();
	
	public static void setPolicy(ContractViolationPolicy aPolicy) {
		// TODO: Make use of Google Guava...
		// ConcurrentInitializer initializer = new AtomicInitializer<ContractViolationPolicy>
		policy = aPolicy;
	}
	
	public static void require(boolean condition) {
		policy.require(condition);
	}
	
	public static void require(Object o, Matcher<?> matcher) {
		policy.require(o, matcher);
	}
	
	public static void require(boolean condition, String msg) {
		policy.require(condition, msg);
	}

	public static void ensure(boolean condition) {
		policy.ensure(condition);
	}

	public static void ensure(Object o, Matcher<?> matcher) {
		policy.ensure(o, matcher);
	}

	public static void ensure(boolean condition, String msg) {
		policy.ensure(condition, msg);
	}

	public static void neverGetHere(String msg) {
		policy.neverGetHere(msg);
	}

	public static void neverGetHere(Throwable t) {
		policy.neverGetHere(t);
	}
}
