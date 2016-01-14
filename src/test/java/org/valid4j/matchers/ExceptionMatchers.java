package org.valid4j.matchers;

import org.hamcrest.Matcher;
import org.valid4j.provider.errors.NeverGetHereViolation;

import static org.hamcrest.Matchers.*;
import static org.junit.internal.matchers.ThrowableMessageMatcher.hasMessage;

public class ExceptionMatchers {

  public static <X extends Throwable> Matcher<X> exceptionWithMessage(String msg) {
    return hasMessage(containsString(msg));
  }

  public static Matcher<NeverGetHereViolation> preventInstantiationViolation() {
    return allOf(
        isA(NeverGetHereViolation.class),
        exceptionWithMessage("Prevent instantiation"));
  }
}
