package org.valid4j;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.*;

public class MessageBuilderBehavior {

  @Test
  public void shouldBuildFormattedMessage() {
    Object m = MessageBuilder.from("This is %d %s", 2, "good");
    assertThat(m.toString(), containsString("This is 2 good"));
  }
  
  @Test
  public void shouldBuildMessageFromInvalidFormat() {
    Object m = MessageBuilder.from("Some %s %d", "message", "with invalid formatting");
    assertThat(m.toString(), containsString("Some %s %d"));
    assertThat(m.toString(), containsString("message"));
    assertThat(m.toString(), containsString("with invalid formatting"));
  }

  @Test
  public void shouldBuildMessageFromTooFewArguments() {
    Object m = MessageBuilder.from("Some %s %s", "message with too few arguments");
    assertThat(m.toString(), containsString("Some %s %s"));
    assertThat(m.toString(), containsString("message with too few arguments"));
  }

  @Test
  public void shouldBuildMessageFromTooManyArguments() {
    Object m = MessageBuilder.from("Some %s", "message", "with too many arguments");
    assertThat(m.toString(), containsString("Some message"));
  }
  
  @Test
  public void shouldBuildMessageFromNullFormat() {
    Object m = MessageBuilder.from(null);
    assertThat(m.toString(), nullValue());
  }

  @Test
  public void shouldBuildMessageFromNullFormatWithArguments() {
    Object m = MessageBuilder.from(null, "argument", 4);
    assertThat(m.toString(), containsString("null"));
    assertThat(m.toString(), containsString("argument"));
    assertThat(m.toString(), containsString("4"));
  }

  @Test
  public void shouldBuildMessageWithNullArguments() {
    Object m = MessageBuilder.from("Some %s %d %b", (Object)null, (Object)null, (Object)null);
    assertThat(m.toString(), containsString("Some null null false"));
  }

  @Test
  public void shouldBuildMismatchMessage() {
    Object m = MessageBuilder.withMismatchOf(3, not(equalTo(2)));
    assertThat(m.toString(), containsString("expected: not <2>"));
    assertThat(m.toString(), containsString("was: <3>"));
  }

  @Test
  public void shouldBuildMismatchMessageOfNullArguments() {
    Object m = MessageBuilder.withMismatchOf(null, notNullValue());
    assertThat(m.toString(), containsString("expected: not null"));
    assertThat(m.toString(), containsString("was: null"));
  }
}
