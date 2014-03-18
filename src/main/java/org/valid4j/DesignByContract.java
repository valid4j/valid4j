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
	
	private static final String NULL_MESSAGE = null;
	
	private static ContractViolationPolicy policy = new DefaultContractViolationPolicy();
	
	public static void setPolicy(ContractViolationPolicy aPolicy) {
		// TODO: Make use of Google Guava...
		// ConcurrentInitializer initializer = new AtomicInitializer<ContractViolationPolicy>
		policy = aPolicy;
	}
	
	public static void require(boolean condition) {
		policy.require(condition, NULL_MESSAGE);
	}
	
	public static <T> T require(T o, Matcher<?> matcher) {
		policy.require(o, matcher);
		return o;
	}
	
	public static void require(boolean condition, String msg, Object... values) {
		policy.require(condition, msg, values);
	}

	public static void ensure(boolean condition) {
		policy.ensure(condition, NULL_MESSAGE);
	}

	public static <T> T ensure(T o, Matcher<?> matcher) {
		policy.ensure(o, matcher);
		return o;
	}

	public static void ensure(boolean condition, String msg, Object... values) {
		policy.ensure(condition, msg, values);
	}

	public static void neverGetHere(String msg) {
		policy.neverGetHere(msg);
	}

	public static void neverGetHere(Throwable t) {
		policy.neverGetHere(t);
	}
}
