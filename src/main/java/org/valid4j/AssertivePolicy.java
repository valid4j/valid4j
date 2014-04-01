package org.valid4j;

import org.hamcrest.Matcher;

public interface AssertivePolicy {

	void check(boolean condition, String msg, Object... values);

	void check(Object o, Matcher<?> matcher);

}
