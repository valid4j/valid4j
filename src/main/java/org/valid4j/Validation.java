package org.valid4j;

import org.hamcrest.Matcher;

public class Validation {

  public static <X extends Throwable> void validate(boolean condition, X exception) throws X {
    if (!condition) {
      throw exception;
    }
  }

  public static <T, X extends Throwable> T validate(T o, Matcher<?> matcher, X exception) throws X {
    if (!matcher.matches(o)) {
      throw exception;
    }
    return o;
  }

}
