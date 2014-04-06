package org.valid4j;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.valid4j.Validation.validate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ValidationBehavior {
  
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @SuppressWarnings("serial")
  public static class CheckedRecoverableException extends Exception {
  }
  
  @Test
  public void shouldPassValidation() throws CheckedRecoverableException {
    validate(true, new CheckedRecoverableException());
  }

  @Test
  public void shouldPassThroughValidArgument() throws CheckedRecoverableException {
    Object argument = new Object();
    Object result = validate(argument, notNullValue(), new CheckedRecoverableException());
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
