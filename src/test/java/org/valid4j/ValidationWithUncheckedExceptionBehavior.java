package org.valid4j;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.valid4j.Validation.otherwiseThrowing;
import static org.valid4j.Validation.validate;

public class ValidationWithUncheckedExceptionBehavior {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void shouldPassValidation() {
    validate(true, new IllegalArgumentException());
    validate(true, IllegalArgumentException.class);
    validate(true, otherwiseThrowing(IllegalArgumentException.class));
  }

  @Test
  public void shouldPassThroughValidArgument() {
    Object argument = new Object();
    Object result = validate(argument, notNullValue(), new IllegalArgumentException());
    assertThat(result, sameInstance(argument));

    result = validate(argument, notNullValue(), IllegalArgumentException.class);
    assertThat(result, sameInstance(argument));

    result = validate(argument, notNullValue(), otherwiseThrowing(IllegalArgumentException.class));
    assertThat(result, sameInstance(argument));
  }

  @Test
  public void shouldThrowWhenValidationFails1() {
    thrown.expect(isA(IllegalArgumentException.class));
    validate(false, new IllegalArgumentException());
  }

  @Test
  public void shouldThrowWhenValidationFails2() {
    thrown.expect(isA(IllegalArgumentException.class));
    validate(false, IllegalArgumentException.class);
  }

  @Test
  public void shouldThrowWhenValidationFails3() {
    thrown.expect(isA(IllegalArgumentException.class));
    validate(false, otherwiseThrowing(IllegalArgumentException.class));
  }

  @Test
  public void shouldThrowWhenMatchValidationFail1() {
    thrown.expect(isA(IllegalArgumentException.class));
    thrown.expectMessage(containsString("message"));
    validate(new Object(), nullValue(), new IllegalArgumentException("message"));
  }

  @Test
  public void shouldThrowWhenMatchValidationFail2() {
    thrown.expect(isA(IllegalArgumentException.class));
    thrown.expectMessage(containsString("expected: null"));
    thrown.expectMessage(containsString("was: "));
    validate(new Object(), nullValue(), IllegalArgumentException.class);
  }

  @Test
  public void shouldThrowWhenMatchValidationFail3() {
    thrown.expect(isA(IllegalArgumentException.class));
    thrown.expectMessage(containsString("expected: null"));
    thrown.expectMessage(containsString("was: "));
    validate(new Object(), nullValue(), otherwiseThrowing(IllegalArgumentException.class));
  }

}
