package org.valid4j;

import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import java.util.Arrays;
import java.util.IllegalFormatException;

/**
 * Class that represents a message and builds the message upon invocation of toString().
 */
public class Message {

  public static Message from(final String format, final Object... args) {
    return new Message() {
      @Override
      public String toString() {
        return withFormattedMessage(format, args);
      }
    };
  }

  public static Message withMismatchOf(final Object actual, final Matcher<?> matcher) {
    return new Message() {
      @Override
      public String toString() {
        return withMismatchMessageOf(actual, matcher);
      }
    };
  }

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
