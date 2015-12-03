package org.valid4j;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.valid4j.Validator.validator;

public class ValidatorBehavior {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	 
	@SuppressWarnings("serial")
	public static class MyRecoverableException extends Exception {
		public MyRecoverableException(String msg) {
			super(msg);
		}
	}

	private final Validator<MyRecoverableException> example = validator(MyRecoverableException.class);

	@Test
	public void shouldPassValidation() throws MyRecoverableException {
		example.validate(true);
	}
	
	@Test
	public void shouldPassValidationWithMessage() throws MyRecoverableException {
		example.validate(true, "This must be %s", "ok");
	}

	@Test
	public void shouldPassMatchingValidation() throws MyRecoverableException {
	  Object argument = new Object();
		Object result = example.validate(argument, notNullValue());
		assertThat(result, sameInstance(argument));
	}
	
	@Test
	public void shouldThrowWhenValidationFails() throws MyRecoverableException {
		thrown.expect(MyRecoverableException.class);
		example.validate(false);
	}

	@Test
	public void shouldThrowWithDescriptiveMessageWhenValidationFails() throws MyRecoverableException {
		thrown.expect(MyRecoverableException.class);
		thrown.expectMessage(containsString("Must be true"));
		example.validate(false, "Must be %s", true);
	}

	@Test
	public void shouldThrowWithDescriptiveMessageWhenMatchingValidationFails() throws MyRecoverableException {
		thrown.expect(MyRecoverableException.class);
		thrown.expectMessage(containsString("expected: not <2>"));
		thrown.expectMessage(containsString("but: was <2>"));
		example.validate(2, not(equalTo(2)));
	}
	
}
