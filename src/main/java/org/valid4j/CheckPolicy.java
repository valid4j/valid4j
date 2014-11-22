package org.valid4j;

import org.hamcrest.Matcher;

/**
 * Policy for checking conditions.
 */
public interface CheckPolicy {

	void check(boolean condition, String msg, Object... values);

	void check(Object o, Matcher<?> matcher);

}
