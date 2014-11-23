package org.valid4j;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.valid4j.exceptions.NeverGetHereViolation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.ThrowableMessageMatcher.hasMessage;

public class ValidationBehavior {

  @Rule
  public ExpectedException thrown = ExpectedException.none().handleAssertionErrors();

  @Test
  public void shouldHavePrivateConstructor() {
    Constructor<?>[] constructors = Validation.class.getDeclaredConstructors();
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

    Constructor<?> constructor = Validation.class.getDeclaredConstructor();
    constructor.setAccessible(true);
    try {
      constructor.newInstance();
    } finally {
      constructor.setAccessible(false);
    }
  }
}
