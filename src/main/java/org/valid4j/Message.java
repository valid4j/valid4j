package org.valid4j;

import java.util.Arrays;
import java.util.IllegalFormatException;

import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

public class Message {

  public static String withFormattedMessage(String msg, Object... values) {
    try {
      if (msg != null) {
        return String.format(msg, values);
      } else if (values.length > 0) {
        return fallbackFormattingOf("null", values);
      } else {
        return null;
      }
    } catch (IllegalFormatException e) {
      return fallbackFormattingOf(msg, values);
    }
  }

  public static String withMismatchMessageOf(Object actual, Matcher<?> matcher) {
    return new StringDescription().
        appendText("expected: ").
        appendDescriptionOf(matcher).
        appendText("\n but was: ").
        appendValue(actual).
        toString();
  }
  
  private static String fallbackFormattingOf(String msg, Object... values) {
    return new StringBuilder(msg).
        append(Arrays.toString(values)).
        toString();
  }
}
