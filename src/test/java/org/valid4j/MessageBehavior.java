package org.valid4j;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class MessageBehavior {

  @Test
  public void shouldBuildFormattedMessage() {
    Object m = Message.from("This is %d %s", 2, "good");
    assertThat(m.toString(), containsString("This is 2 good"));
  }
  
  @Test
  public void shouldBuildMessageFromInvalidFormat() {
    Object m = Message.from("Some %s %d", "message", "with invalid formatting");
    assertThat(m.toString(), containsString("Some %s %d"));
    assertThat(m.toString(), containsString("message"));
    assertThat(m.toString(), containsString("with invalid formatting"));
  }

  @Test
  public void shouldBuildMessageFromTooFewArguments() {
    Object m = Message.from("Some %s %s", "message with too few arguments");
    assertThat(m.toString(), containsString("Some %s %s"));
    assertThat(m.toString(), containsString("message with too few arguments"));
  }

  @Test
  public void shouldBuildMessageFromTooManyArguments() {
    Object m = Message.from("Some %s", "message", "with too many arguments");
    assertThat(m.toString(), containsString("Some message"));
  }
  
  @Test
  public void shouldBuildMessageFromNullFormat() {
    Object m = Message.from(null);
    assertThat(m.toString(), nullValue());
  }

  @Test
  public void shouldBuildMessageFromNullFormatWithArguments() {
    Object m = Message.from(null, "argument", 4);
    assertThat(m.toString(), containsString("null"));
    assertThat(m.toString(), containsString("argument"));
    assertThat(m.toString(), containsString("4"));
  }

  @Test
  public void shouldBuildMessageWithNullArguments() {
    Object m = Message.from("Some %s %d %b", (Object) null, (Object) null, (Object) null);
    assertThat(m.toString(), containsString("Some null null false"));
  }

  @Test
  public void shouldBuildMismatchMessage() {
    Object m = Message.withMismatchOf(3, not(equalTo(2)));
    assertThat(m.toString(), containsString("expected: not <2>"));
    assertThat(m.toString(), containsString("was: <3>"));
  }

  @Test
  public void shouldBuildMismatchMessageOfNullArguments() {
    Object m = Message.withMismatchOf(null, notNullValue());
    assertThat(m.toString(), containsString("expected: not null"));
    assertThat(m.toString(), containsString("was: null"));
  }
}
