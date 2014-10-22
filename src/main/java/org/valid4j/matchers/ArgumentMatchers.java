package org.valid4j.matchers;

import org.hamcrest.Matcher;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.isEmptyOrNullString;

/**
 * Common convenience matchers for arguments
 */
public class ArgumentMatchers {

  public static Matcher<Object> notNullArg() {
    return describedAs("not null", notNullValue());
  }

  public static Matcher<Object> notNullArg(final String name) {
    return describedAs("%0 not null", notNullValue(), name);
  }

  public static Matcher<String> notEmptyArg() {
    return describedAs("not empty", not(isEmptyOrNullString()));
  }

  public static Matcher<String> notEmptyArg(final String name) {
    return describedAs("%0 not empty", not(isEmptyOrNullString()), name);
  }
}
