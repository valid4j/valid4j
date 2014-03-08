package org.valid4j;

import org.hamcrest.Matcher;

public interface ContractViolationPolicy {

	void require(boolean condition);

	void require(boolean condition, String msg);

	void require(Object o, Matcher<?> matcher);
	
	void ensure(boolean condition);

	void ensure(boolean condition, String msg);

	void ensure(Object o, Matcher<?> matcher);

	void neverGetHere(Throwable t);

	void neverGetHere(String msg);

}
