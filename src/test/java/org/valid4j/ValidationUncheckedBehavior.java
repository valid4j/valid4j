package org.valid4j;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.valid4j.Validation.validate;

public class ValidationUncheckedBehavior {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void shouldPassValidation() {
    validate(true, NullPointerException.class);
  }

  @Test
  public void shouldPassThroughValidArgument() {
    Object argument = new Object();
    Object result = validate(argument, notNullValue(), NullPointerException.class);
    assertThat(result, sameInstance(argument));
  }

  @Test
  public void shouldThrowWhenValidationFails() {
    thrown.expect(isA(NullPointerException.class));
    validate(false, NullPointerException.class);
  }

  @Test
  public void shouldThrowWhenMatchValidationFails() {
    thrown.expect(isA(NullPointerException.class));
    thrown.expectMessage(containsString("expected: null"));
    thrown.expectMessage(containsString("was: "));
    validate(new Object(), nullValue(), NullPointerException.class);
  }
}
