package org.valid4j;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
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
  public static class MyException extends RuntimeException {
  }

  @Test
  public void shouldPass() {
    validate(true, new MyException());
  }

  @Test
  public void shouldThrowWhenValidationFails() {
    thrown.expect(MyException.class);
    validate(false, new MyException());
  }
  
  @Test
  public void shouldPassArgument() {
    Integer argument = 7;
    Integer result = validate(argument, is(equalTo(7)), new MyException());
    assertThat(result, sameInstance(argument));
  }
  
  @Test
  public void shouldThrowWhenMatchValidationFails() {
    thrown.expect(MyException.class);
    validate(new Object(), is(false), new MyException());
  }
}
