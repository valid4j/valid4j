package org.valid4j;

import org.hamcrest.Matcher;

public interface ContractViolationPolicy {

	void require(boolean condition, String msg, Object... values);

	void require(Object o, Matcher<?> matcher);
	
	void ensure(boolean condition, String msg, Object... values);

	void ensure(Object o, Matcher<?> matcher);

	void neverGetHere(String msg, Object... values);
	
	void neverGetHere(Throwable t, String msg, Object... values);

}
