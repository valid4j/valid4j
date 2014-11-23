package org.valid4j.matchers;

import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.valid4j.exceptions.NeverGetHereViolation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.ThrowableMessageMatcher.hasMessage;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.valid4j.matchers.ArgumentMatchers.named;
import static org.valid4j.matchers.ArgumentMatchers.notEmptyString;

/**
 * Testing the behavior of argument matchers
 */
public class ArgumentMatchersBehavior {

  @Rule
  public ExpectedException thrown = ExpectedException.none().handleAssertionErrors();

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

  @Test
  public void shouldHavePrivateConstructor() {
    Constructor<?>[] constructors = ArgumentMatchers.class.getDeclaredConstructors();
    assertThat(constructors.length, equalTo(1));
    assertThat(Modifier.isPrivate(constructors[0].getModifiers()), is(true));
  }

  @Test
  public void shouldPreventInstantiation() throws Exception {
    thrown.expect(InvocationTargetException.class);
    thrown.expectCause(
        allOf(
            isA(NeverGetHereViolation.class),
            hasMessage(containsString("Prevent instantiation"))));

    Constructor<?> constructor = ArgumentMatchers.class.getDeclaredConstructor();
    constructor.setAccessible(true);
    try {
      constructor.newInstance();
    } finally {
      constructor.setAccessible(false);
    }
  }

}
