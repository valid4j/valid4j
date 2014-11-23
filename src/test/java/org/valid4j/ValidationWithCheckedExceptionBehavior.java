package org.valid4j;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.valid4j.Validation.otherwiseThrowing;
import static org.valid4j.Validation.validate;

public class ValidationWithCheckedExceptionBehavior {
  
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @SuppressWarnings("serial")
  public static class CheckedRecoverableException extends Exception {
  }
  
  @Test
  public void shouldPassValidation() throws CheckedRecoverableException {
    validate(true, new CheckedRecoverableException());
    validate(true, CheckedRecoverableException.class);
    validate(true, otherwiseThrowing(CheckedRecoverableException.class));
  }

  @Test
  public void shouldPassThroughValidArgument() throws CheckedRecoverableException {
    Object argument = new Object();
    Object result = validate(argument, notNullValue(), new CheckedRecoverableException());
    assertThat(result, sameInstance(argument));

    result = validate(argument, notNullValue(), CheckedRecoverableException.class);
    assertThat(result, sameInstance(argument));

    result = validate(argument, notNullValue(), otherwiseThrowing(CheckedRecoverableException.class));
    assertThat(result, sameInstance(argument));
  }

  @Test
  public void shouldThrowWhenValidationFails() throws CheckedRecoverableException {
    final CheckedRecoverableException recoverableException = new CheckedRecoverableException();
    thrown.expect(sameInstance(recoverableException));
    validate(false, recoverableException);
  }
  
  @Test
  public void shouldThrowWhenMatchValidationFails() throws CheckedRecoverableException {
    final CheckedRecoverableException recoverableException = new CheckedRecoverableException();
    thrown.expect(sameInstance(recoverableException));
    validate(new Object(), nullValue(), recoverableException);
  }
}
