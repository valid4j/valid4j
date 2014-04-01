package org.valid4j;

import java.util.Arrays;
import java.util.IllegalFormatException;

import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

public class Message {

  public static String withFormattedMessage(String msg, Object... values) {
    try {
      return msg != null ? String.format(msg, values) : null;
    } catch (IllegalFormatException e) {
      StringBuilder sb = new StringBuilder(msg);
      sb.append(Arrays.toString(values));
      return sb.toString();
    }
  }

  public static String withMismatchMessageOf(Object actual, Matcher<?> matcher) {
    return new StringDescription().appendText("expected: ")
        .appendDescriptionOf(matcher).appendText("\n but was: ")
        .appendValue(actual).toString();
  }

}
