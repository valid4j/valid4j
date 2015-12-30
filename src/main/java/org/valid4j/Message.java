package org.valid4j;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import java.util.Arrays;
import java.util.IllegalFormatException;

/**
 * Class that represents a message, but builds the message first upon invocation of toString().
 */
public class Message {

  public static Message describing(final String format, final Object... args) {
    return new Message() {
      @Override
      public String toString() {
        return withFormattedMessage(format, args);
      }
    };
  }

  public static Message describingMismatchOf(final Object actual, final Matcher<?> matcher) {
    return new Message() {
      @Override
      public String toString() {
        return withMismatchMessageOf(actual, matcher);
      }
    };
  }

  private static String withFormattedMessage(String msg, Object... values) {
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

  private static String withMismatchMessageOf(Object actual, Matcher<?> matcher) {
    Description mismatchDescription = new StringDescription()
        .appendText("expected: ")
        .appendDescriptionOf(matcher)
        .appendText("\n but: ");
    matcher.describeMismatch(actual, mismatchDescription);
    return mismatchDescription.toString();
  }

  private static String fallbackFormattingOf(String msg, Object... values) {
    return new StringBuilder(msg)
        .append(Arrays.toString(values))
        .toString();
  }
}
