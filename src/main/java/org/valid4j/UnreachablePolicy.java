package org.valid4j;

public interface UnreachablePolicy {

  void neverGetHere(Throwable t, String msg, Object... values);

}
