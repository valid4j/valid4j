package org.valid4j.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static org.hamcrest.CoreMatchers.describedAs;
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * Common convenience matchers for arguments
 */
public class ArgumentMatchers {

  public static Matcher<Object> isNotNull() {
    return describedAs("not null", notNullValue());
  }

  public static Matcher<Object> isNotNull(final String argumentName) {
    return describedAs("%0 not null", notNullValue(), argumentName);
  }

  public static Matcher<String> isNotEmptyString() {
    return new TypeSafeMatcher<String>() {
      @Override
      protected boolean matchesSafely(String s) {
        return !s.isEmpty();
      }

      @Override
      public void describeTo(Description description) {
        description.appendText("not empty");

      }
    };
  }

  public static Matcher<String> isNotEmptyString(final String argumentName) {
    return describedAs("%0 not empty", isNotEmptyString(), argumentName);
  }
}
