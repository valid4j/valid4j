package org.valid4j;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.InvocationTargetException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.valid4j.ExceptionFactories.exception;
import static org.valid4j.matchers.ConstructionHelper.tryToInstantiate;
import static org.valid4j.matchers.ConstructorMatchers.classWithPrivateConstructor;
import static org.valid4j.matchers.ExceptionMatchers.preventInstantiationViolation;

public class ExceptionFactoryBehavior {

	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	@SuppressWarnings("serial")
	public static class OneStringArgumentException extends RuntimeException {
		@SuppressWarnings("unused")
		public OneStringArgumentException(String msg) {
			super(msg);
		}
		@SuppressWarnings("unused")
		public OneStringArgumentException() {
		}
		@SuppressWarnings("unused")
		public OneStringArgumentException(String msg, Throwable t) {
			super(msg, t);
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
	public static abstract class AbstractException extends RuntimeException {
		public AbstractException(String msg) {
			super(msg);
		}
	}

	@SuppressWarnings("serial")
	public static class NoPublicConstructorException extends RuntimeException {
		@SuppressWarnings("unused")
		private NoPublicConstructorException(String msg) {
			super(msg);
		}

		@SuppressWarnings("unused")
		private NoPublicConstructorException() {
		}
	}

	@SuppressWarnings("serial")
	public static class SelfThrowingException extends RuntimeException {
		public SelfThrowingException(String msg) {
			super(msg);
			throw null;
		}
	}

	@SuppressWarnings("serial")
	public static class SelfThrowingNoArgumentException extends RuntimeException {
		public SelfThrowingNoArgumentException() {
			throw null;
		}
	}

	@Test
	public void shouldBuildExceptionWithPublicOneStringArgumentConstructor() {
		ExceptionFactory<OneStringArgumentException> exception = exception(OneStringArgumentException.class);
		RuntimeException e = exception.newInstance("exception message");
		assertThat(e, instanceOf(OneStringArgumentException.class));
		assertThat(e.getMessage(), equalTo("exception message"));
	}
	
	@Test
	public void shouldBuildExceptionWithNullMessage() {
		ExceptionFactory<OneStringArgumentException> exception = exception(OneStringArgumentException.class);
		RuntimeException e = exception.newInstance(null);
		assertThat(e, instanceOf(OneStringArgumentException.class));
		assertThat(e.getMessage(), nullValue());
	}
	
	@Test
	public void shouldBuildExceptionWithNoArgumentConstructor() {
		ExceptionFactory<NoArgumentException> exception = exception(NoArgumentException.class);
		RuntimeException e = exception.newInstance("exception message");
		assertThat(e, instanceOf(NoArgumentException.class));
		assertThat(e.getMessage(), nullValue());
	}

	@Test
	public void shouldRejectExceptionWithNoPublicConstructor() {
		thrown.expect(instanceOf(AssertionError.class));
		thrown.expectMessage("must have a public constructor");
		exception(NoPublicConstructorException.class);
	}

	@Test
	public void shouldRejectInnerClassExceptions() {
		thrown.expect(instanceOf(AssertionError.class));
		exception(InnerClassException.class);
	}

	@Test
	public void shouldRejectAbstractExceptions() {
		thrown.expect(instanceOf(AssertionError.class));
		exception(AbstractException.class);
	}
	
	@Test
	public void shouldRejectExceptionWithNoStringArgumentConstructor() {
		thrown.expect(instanceOf(AssertionError.class));
		exception(NoStringArgumentException.class);
	}

	@Test
	public void shouldFailOnOneArgumentExceptionThatThrowByThemselves() {
		ExceptionFactory<SelfThrowingException> exception = exception(SelfThrowingException.class);
		thrown.expect(instanceOf(AssertionError.class));
		exception.newInstance("exception message");
	}

	@Test
	public void shouldFailOnNoArgumentExceptionThatThrowByThemselves() {
		ExceptionFactory<SelfThrowingNoArgumentException> exception = exception(SelfThrowingNoArgumentException.class);
		thrown.expect(instanceOf(AssertionError.class));
		exception.newInstance("exception message");
	}

	@Test
	public void shouldHavePrivateConstructor() {
		assertThat(ExceptionFactories.class, classWithPrivateConstructor());
	}

	@Test
	public void shouldPreventInstantiation() throws Exception {
		thrown.expect(InvocationTargetException.class);
		thrown.expectCause(preventInstantiationViolation());
		tryToInstantiate(ExceptionFactories.class);
	}
}
