package org.valid4j;

/**
 * Class that constructs a runtime exception given a string argument.
 */
public interface ExceptionFactory<T extends RuntimeException> {

	T newInstance(String msg);
	
}
