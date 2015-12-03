package org.valid4j;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.valid4j.exceptions.EnsureViolation;
import org.valid4j.exceptions.NeverGetHereViolation;
import org.valid4j.exceptions.RequireViolation;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.valid4j.Assertive.*;
import static org.valid4j.matchers.ConstructionHelper.tryToInstantiate;
import static org.valid4j.matchers.ConstructorMatchers.classWithPrivateConstructor;
import static org.valid4j.matchers.ExceptionMatchers.preventInstantiationViolation;

public class AssertiveDefaultBehavior {
	
	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	@Test
	public void shouldPassWhenRequiredConditionIsTrue() {
		require(true);
	}
	
	@Test
	public void shouldPassWhenDescribedRequiredConditionIsTrue() {
		require(true, "Should pass when required condition is true");
	}
	
	@Test
	public void shouldPassWhenNullDescribedRequiredConditionIsTrue() {
		require(true, nullMessage());
	}
	
	@Test
	public void shouldPassWhenArgumentDescribedRequiredConditionIsTrue() {
		require(true, "Some %s and %s", "this", "that");
	}
	
	@Test
	public void shouldPassWhenRequiredConditionMatches() {
		require(3, equalTo(3));
	}
	
	@Test
	public void shouldPassValidObjectAsRequiredResult() {
		Date argument = new Date();
		Date result = require(argument, notNullValue());
		assertThat(result, sameInstance(argument));
	}

	@Test
	public void shouldPassWhenEnsuredConditionIsTrue() {
		ensure(true);
	}
	
	@Test
	public void shouldPassWhenDescribedEnsuredConditionIsTrue() {
		ensure(true, "Should pass when ensured condition is true");
	}
	
	@Test
	public void shouldPassWhenNullDescribedEnsuredConditionIsTrue() {
		ensure(true, nullMessage());
	}
	
	@Test
	public void shouldPassWhenArgumentDescribedEnsuredConditionIsTrue() {
		ensure(true, "%s and %s", "This", "that");
	}
	
	@Test
	public void shouldPassWhenEnsuredConditionMatches() {
		ensure(4, equalTo(4));
	}
	
	@Test
	public void shouldPassValidObjectAsEnsuredResult() {
		Date argument = new Date();
		Date result = ensure(argument, notNullValue());
		assertThat(result, sameInstance(argument));
	}

	@Test
	public void shouldThrowWhenRequireFails() {
		thrown.expect(RequireViolation.class);
		require(false);
	}
	
	@Test
	public void shouldThrowWithMessageWhenRequireFails() {
		thrown.expect(RequireViolation.class);
		thrown.expectMessage(containsString("some message"));
		require(false, "some %s", "message");
	}

	@Test
	public void shouldThrowWithIllegalFormatMessageWhenRequireFails() {
		thrown.expect(RequireViolation.class);
		thrown.expectMessage(containsString("some %s %d"));
		thrown.expectMessage(containsString("message"));
		thrown.expectMessage(containsString("hi"));
		require(false, "some %s %d", "message", "hi");
	}

	@Test
	public void shouldThrowWithMatchingMessageWhenRequireFails() {
		thrown.expect(RequireViolation.class);
		thrown.expectMessage(containsString("expected: not <2>"));
		thrown.expectMessage(containsString("but: was <2>"));
		require(2, not(equalTo(2)));
	}

	@Test
	public void shouldThrowWhenEnsureFails() {
		thrown.expect(EnsureViolation.class);
		ensure(false);
	}

	@Test
	public void shouldThrowWithMessageWhenEnsureFails() {
		thrown.expect(EnsureViolation.class);
		thrown.expectMessage(containsString("this should fail"));
		ensure(false, "this should fail");
	}

	@Test
	public void shouldThrowWithIllegalFormatMessageWhenEnsureFails() {
		thrown.expect(EnsureViolation.class);
		thrown.expectMessage(containsString("some %s %d"));
		thrown.expectMessage(containsString("message"));
		thrown.expectMessage(containsString("hi"));
		ensure(false, "some %s %d", "message", "hi");
	}

	@Test
	public void shouldThrowWithMatchingMessageWhenEnsureFails() {
		thrown.expect(EnsureViolation.class);
		thrown.expectMessage(containsString("but: was <3>"));
		thrown.expectMessage(containsString("expected: <5>"));
		ensure(3, equalTo(5));
	}

	@Test
	public void shouldThrowWhenReachingUnreachableCode() {
		thrown.expect(NeverGetHereViolation.class);
		neverGetHere();
	}

  @Test
	public void shouldThrowWhenReachingUnexpectedException() {
		final Throwable cause = new UnsupportedEncodingException();
		thrown.expect(NeverGetHereViolation.class);
		thrown.expectCause(sameInstance(cause));
		neverGetHere(cause);
	}

	@Test
	public void shouldThrowWithMessageWhenReachingUnreachableCode() {
		thrown.expect(NeverGetHereViolation.class);
		thrown.expectMessage(containsString("something"));
		neverGetHere("something");
	}

	@Test
	public void shouldThrowWithFormattedMessageWhenReachingUnreachableCode() {
		thrown.expect(NeverGetHereViolation.class);
		thrown.expectMessage(containsString("some 3"));
		neverGetHere("some %d", 3);
	}
	
	@Test
	public void shouldThrowWithMessageWhenReachingUnexpectedException() {
		final Throwable t = new UnsupportedEncodingException();
		thrown.expect(NeverGetHereViolation.class);
		thrown.expectMessage(containsString("any message"));
		thrown.expectCause(sameInstance(t));
		neverGetHere(t, "any message");
	}

	@Test
	public void shouldThrowErrorWhenReachingUnreachableCode() {
		thrown.expect(NeverGetHereViolation.class);
		throw neverGetHereError();
	}

	@Test
	public void shouldThrowErrorWhenReachingUnexpectedException() {
		final Throwable cause = new UnsupportedEncodingException();
		thrown.expect(NeverGetHereViolation.class);
		thrown.expectCause(sameInstance(cause));
		throw neverGetHereError(cause);
	}

	@Test
	public void shouldThrowErrorWithMessageWhenReachingUnreachableCode() {
		thrown.expect(NeverGetHereViolation.class);
		thrown.expectMessage(containsString("something"));
		throw neverGetHereError("something");
	}

	@Test
	public void shouldThrowErrorWithFormattedMessageWhenReachingUnreachableCode() {
		thrown.expect(NeverGetHereViolation.class);
		thrown.expectMessage(containsString("some 3"));
		throw neverGetHereError("some %d", 3);
	}

	@Test
	public void shouldThrowErrorWithMessageWhenReachingUnexpectedException() {
		final Throwable t = new UnsupportedEncodingException();
		thrown.expect(NeverGetHereViolation.class);
		thrown.expectMessage(containsString("any message"));
		thrown.expectCause(sameInstance(t));
		throw neverGetHereError(t, "any message");
	}

	@Test
	public void shouldHavePrivateConstructor() {
		assertThat(Assertive.class, classWithPrivateConstructor());
	}

	@Test
	public void shouldPreventInstantiation() throws Exception {
		thrown.expect(InvocationTargetException.class);
		thrown.expectCause(preventInstantiationViolation());
		tryToInstantiate(Assertive.class);
	}

	// Return null message as a String in order to let the compiler bind to proper method.
	private static String nullMessage() {
		return null;
	}

}
