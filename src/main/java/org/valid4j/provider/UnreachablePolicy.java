package org.valid4j.provider;

/**
 * Policy for handling unreachable code
 */
public interface UnreachablePolicy {

  void neverGetHere(Throwable t, Object messageBuilder);

}
