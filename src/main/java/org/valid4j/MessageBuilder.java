package org.valid4j;

import org.hamcrest.*;

/**
 * Class that builds a message upon invocation of toString().
 */
public class MessageBuilder {

  public static MessageBuilder from(final String format, final Object... args) {
    return new MessageBuilder() {
      @Override
      public String toString() {
        return Message.withFormattedMessage(format, args);
      }
    };
  }
  
  public static MessageBuilder withMismatchOf(final Object actual, final Matcher<?> matcher) {
    return new MessageBuilder() {
      @Override
      public String toString() {
        return Message.withMismatchMessageOf(actual, matcher);
      }
    };
  }
}
