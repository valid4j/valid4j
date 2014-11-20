package org.valid4j.matchers;

import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.valid4j.matchers.ArgumentMatchers.named;
import static org.valid4j.matchers.ArgumentMatchers.notEmptyString;

/**
 * Testing the behavior of argument matchers
 */
public class ArgumentMatcherBehavior {

  @Test
  public void shouldDecorateWrappedMatcherWithName() {
    Matcher<Object> matcher = mock(Matcher.class);
    Matcher<Object> named = named("any name", matcher);

    StringDescription description = new StringDescription();
    named.describeTo(description);
    assertThat(description.toString(), startsWith("\"any name\""));
    assertThat(named.toString(), startsWith("\"any name\""));
  }

  @Test
  public void shouldInvokeWrappedMatcher() {
    Matcher<Object> matcher = mock(Matcher.class);
    Matcher<Object> named = named("any name", matcher);

    Object argument = new Object();
    named.matches(argument);
    verify(matcher).matches(argument);
  }

  @Test
  public void shouldMatchNotEmptyString() {
    assertThat("some string", notEmptyString());
  }

  @Test
  public void shouldNotMatchEmptyString() {
    assertThat("", not(notEmptyString()));
  }

  @Test
  public void shouldNotMatchNullForEmptyString() {
    assertThat(null, not(notEmptyString()));
  }
}
