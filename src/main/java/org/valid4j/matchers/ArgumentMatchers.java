package org.valid4j.matchers;

import org.hamcrest.Matcher;

import static org.hamcrest.CoreMatchers.describedAs;
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * Common convenience matchers for arguments
 */
public class ArgumentMatchers {

  public static Matcher<Object> notNullArg(final String argumentName) {
    return describedAs("%0 not null", notNullValue(), argumentName);
  }
}
