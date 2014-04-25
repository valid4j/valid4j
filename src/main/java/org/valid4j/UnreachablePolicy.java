package org.valid4j;

/**
 * Policy for handling unreachable code
 */
public interface UnreachablePolicy {

  void neverGetHere(Throwable t, String msg, Object... values);

}
