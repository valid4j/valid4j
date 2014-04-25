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
    Object m = MessageBuilder.from("Some %s %d", "message", "with invalid formatting", 4);
    assertThat(m.toString(), containsString("Some %s %d"));
    assertThat(m.toString(), containsString("message"));
    assertThat(m.toString(), containsString("with invalid formatting"));
    assertThat(m.toString(), containsString("4"));
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
