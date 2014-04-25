package org.valid4j;

import org.hamcrest.Matcher;

/**
 * Policy for checking contract conditions.
 */
public interface AssertivePolicy {

	void check(boolean condition, String msg, Object... values);

	void check(Object o, Matcher<?> matcher);

}
