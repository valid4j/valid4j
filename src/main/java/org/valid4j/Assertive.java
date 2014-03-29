package org.valid4j;

import org.hamcrest.*;

/**
 * Entry point for the global assertive policy.
 * 
 */
public class Assertive {
	
	private static final String NULL_MESSAGE = null;
	private static final Throwable NO_CAUSE = null;
	
	private static AssertivePolicy policy = new CheckAssertivePolicy();
	
	public static void setPolicy(AssertivePolicy aPolicy) {
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

	public static void neverGetHere() {
		policy.neverGetHere(NO_CAUSE, NULL_MESSAGE);
	}

	public static void neverGetHere(String msg) {
		policy.neverGetHere(NO_CAUSE, msg);
	}

	public static void neverGetHere(String msg, Object... values) {
		policy.neverGetHere(NO_CAUSE, msg, values);
	}
		
	public static void neverGetHere(Throwable t) {
		policy.neverGetHere(t, NULL_MESSAGE);
	}

	public static void neverGetHere(Throwable t, String msg, Object... values) {
		policy.neverGetHere(t, msg, values);
	}

}
