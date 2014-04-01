package org.valid4j;

import org.hamcrest.*;

/**
 * Entry point for the global assertive policy.
 * 
 */
public class Assertive {
	
	private static final String NULL_MESSAGE = null;
	private static final Throwable NO_CAUSE = null;
	
	private static AssertivePolicy requirePolicy = new CheckAssertivePolicy(new RequireViolationPolicy());
	private static AssertivePolicy ensurePolicy = new CheckAssertivePolicy(new EnsureViolationPolicy());
	private static UnreachablePolicy neverGetHerePolicy = new NeverGetHerePolicy();
		
	public static void require(boolean condition) {
	  requirePolicy.check(condition, NULL_MESSAGE);
	}
	
	public static <T> T require(T o, Matcher<?> matcher) {
	  requirePolicy.check(o, matcher);
		return o;
	}
	
	public static void require(boolean condition, String msg, Object... values) {
	  requirePolicy.check(condition, msg, values);
	}

	public static void ensure(boolean condition) {
	  ensurePolicy.check(condition, NULL_MESSAGE);
	}

	public static <T> T ensure(T o, Matcher<?> matcher) {
	  ensurePolicy.check(o, matcher);
		return o;
	}

	public static void ensure(boolean condition, String msg, Object... values) {
	  ensurePolicy.check(condition, msg, values);
	}

	public static void neverGetHere() {
	  neverGetHerePolicy.neverGetHere(NO_CAUSE, NULL_MESSAGE);
	}

	public static void neverGetHere(String msg) {
	  neverGetHerePolicy.neverGetHere(NO_CAUSE, msg);
	}

	public static void neverGetHere(String msg, Object... values) {
	  neverGetHerePolicy.neverGetHere(NO_CAUSE, msg, values);
	}
		
	public static void neverGetHere(Throwable t) {
	  neverGetHerePolicy.neverGetHere(t, NULL_MESSAGE);
	}

	public static void neverGetHere(Throwable t, String msg, Object... values) {
	  neverGetHerePolicy.neverGetHere(t, msg, values);
	}

}
