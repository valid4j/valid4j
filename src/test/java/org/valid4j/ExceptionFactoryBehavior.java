package org.valid4j;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.valid4j.ExceptionFactories.*;

import org.junit.*;

public class ExceptionFactoryBehavior {

	@SuppressWarnings("serial")
	public static class OneStringArgumentException extends RuntimeException {
		public OneStringArgumentException(String msg) {
			super(msg);
		}
	}

	@SuppressWarnings("serial")
	public static class NoArgumentException extends RuntimeException {
		public NoArgumentException() {
			super();
		}
	}
	
	@SuppressWarnings("serial")
	public static class NoStringArgumentException extends RuntimeException {
		public NoStringArgumentException(Throwable t) {
			super(t);
		}
	}

	@SuppressWarnings("serial")
	public class InnerClassException extends RuntimeException {
		public InnerClassException(String msg) {
			super(msg);
		}
	}

	@SuppressWarnings("serial")
	public static class NoPublicConstructorException extends RuntimeException {
		private NoPublicConstructorException(String msg) {
			super(msg);
		}
	}
	
	@Test
	public void shouldBuildExceptionWithPublicOneStringArgumentConstructor() {
		ExceptionFactory<OneStringArgumentException> builder = builder(OneStringArgumentException.class);
		RuntimeException e = builder.newInstance("exception message");
		assertThat(e, instanceOf(OneStringArgumentException.class));
		assertThat(e.getMessage(), equalTo("exception message"));
	}
	
	@Test
	public void shouldBuildExceptionWithNullMessage() {
		ExceptionFactory<OneStringArgumentException> builder = builder(OneStringArgumentException.class);
		RuntimeException e = builder.newInstance(null);
		assertThat(e, instanceOf(OneStringArgumentException.class));
		assertThat(e.getMessage(), nullValue());
	}
	
	@Test
	public void shouldBuildExceptionWithNoArgumentConstructor() {
		ExceptionFactory<NoArgumentException> builder = builder(NoArgumentException.class);
		RuntimeException e = builder.newInstance("exception message");
		assertThat(e, instanceOf(NoArgumentException.class));
		assertThat(e.getMessage(), nullValue());
	}
	
	@Test (expected = AssertionError.class)
	public void shouldRejectInnerClassExceptions() {
		// Inner classes can not be instantiated using a one string argument constructor.
		builder(InnerClassException.class);
	}
	
	@Test (expected = AssertionError.class)
	public void shouldRejectExceptionWithNoPublicConstructor() {
		builder(NoPublicConstructorException.class);
	}

	@Test (expected = AssertionError.class)
	public void shouldRejectExceptionWithNoStringArgumentConstructor() {
		builder(NoStringArgumentException.class);
	}

}
