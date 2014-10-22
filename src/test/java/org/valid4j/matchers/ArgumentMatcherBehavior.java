package org.valid4j.matchers;

import org.hamcrest.StringDescription;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.valid4j.matchers.ArgumentMatchers.notNullArg;

/**
 * Testing the behavior of argument matchers
 */
public class ArgumentMatcherBehavior {

  @Test
  public void shouldMatchNotNullArguments() {
    assertThat(new Object(), notNullArg("myArgument"));
    assertThat(null, not(notNullArg("someArgument")));

    StringDescription description = new StringDescription();
    notNullArg("my-parameter").describeTo(description);
    assertThat(description.toString(), equalTo("\"my-parameter\" not null"));
  }
}
