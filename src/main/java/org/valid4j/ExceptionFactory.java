package org.valid4j;

/**
 * Factory that constructs a recoverable exception given a string argument.
 */
public interface ExceptionFactory<T extends Exception> {

	T newInstance(String msg);
	
}
