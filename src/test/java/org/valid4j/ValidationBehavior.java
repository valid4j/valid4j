package org.valid4j;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.valid4j.Validator.*;

import org.junit.*;
import org.junit.rules.*;

public class ValidationBehavior {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	 
	@SuppressWarnings("serial")
	public static class MyValidationException extends RuntimeException {
		public MyValidationException(String msg) {
			super(msg);
		}
	}

	private final Validator<MyValidationException> validate = validator(MyValidationException.class);

	@Test
	public void shouldPassValidation() {
		validate.argument(true);
	}
	
	@Test
	public void shouldPassValidationWithMessage() {
		validate.argument(true, "This must be %s", "ok");
	}

	@Test
	public void shouldPassMatchingValidation() {
		int value = validate.argument(3, equalTo(3));
		assertThat(value, equalTo(3));
	}
	
	@Test
	public void shouldThrowWhenValidationFails() {
		thrown.expect(MyValidationException.class);
		thrown.expectMessage(equalTo("the validated expression is false"));
		
		validate.argument(false);
	}

	@Test
	public void shouldThrowWithDescriptiveMessageWhenValidationFails() {
		thrown.expect(MyValidationException.class);
		thrown.expectMessage(containsString("Must be true"));
		
		validate.argument(false, "Must be %s", true);
	}

	@Test
	public void shouldThrowWithDescriptiveMessageWhenMatchingValidationFails() {
		thrown.expect(MyValidationException.class);
		thrown.expectMessage(containsString("expected: not <2>"));
		thrown.expectMessage(containsString("was: <2>"));
		
		validate.argument(2, not(equalTo(2)));
	}
	
}
