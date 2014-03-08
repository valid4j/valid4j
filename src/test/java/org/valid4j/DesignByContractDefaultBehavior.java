package org.valid4j;

import java.io.*;

import org.junit.*;
import org.junit.rules.*;
import org.valid4j.exceptions.*;

import static org.hamcrest.CoreMatchers.*;
import static org.valid4j.DesignByContract.*;

public class DesignByContractDefaultBehavior {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void shouldPassWhenRequiredConditionIsTrue() {
		require(true);
	}
	
	@Test
	public void shouldPassWhenDescribedRequiredConditionIsTrue() {
		require(true, "Should pass when required condition is true");
	}
	
	@Test
	public void shouldPassWhenRequiredConditionIsMatched() {
		require(3, equalTo(3));
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
	public void shouldPassWhenEnsuredConditionIsMatched() {
		ensure(4, equalTo(4));
	}

	@Test
	public void shouldThrowWhenRequireFails() {
		thrown.expect(RequireViolation.class);
		thrown.expectMessage(containsString("require violation"));
		
		require(false);
	}
	
	@Test
	public void shouldThrowWithMessageWhenRequireFails() {
		thrown.expect(RequireViolation.class);
		thrown.expectMessage(containsString("require violation"));
		thrown.expectMessage(containsString("some message"));
		
		require(false, "some message");
	}

	@Test
	public void shouldThrowWithMatchingMessageWhenRequireFails() {
		thrown.expect(RequireViolation.class);
		thrown.expectMessage(containsString("require violation"));
		thrown.expectMessage(containsString("expected: not <2>"));
		thrown.expectMessage(containsString("was: <2>"));
		
		require(2, not(equalTo(2)));
	}

	@Test
	public void shouldThrowWhenEnsureFails() {
		thrown.expect(EnsureViolation.class);
		thrown.expectMessage(containsString("ensure violation"));
		
		ensure(false);
	}

	@Test
	public void shouldThrowWithMessageWhenEnsureFails() {
		thrown.expect(EnsureViolation.class);
		thrown.expectMessage(containsString("ensure violation"));
		thrown.expectMessage(containsString("this should fail"));
		
		ensure(false, "this should fail");
	}

	@Test
	public void shouldThrowWithMatchingMessageWhenEnsureFails() {
		thrown.expect(EnsureViolation.class);
		thrown.expectMessage(containsString("ensure violation"));
		thrown.expectMessage(containsString("was: <3>"));
		thrown.expectMessage(containsString("expected: <5>"));
		
		ensure(3, equalTo(5));
	}
	
	@Test
	public void shouldThrowWhenReachingUnreachableCode() {
		thrown.expect(NeverGetHereViolation.class);
		thrown.expectMessage(containsString("never get here"));
		thrown.expectMessage(containsString("some message"));
		
		neverGetHere("some message");
	}
	
	@Test
	public void shouldThrowWhenReachingUnexpectedException() {
		final Throwable t = new UnsupportedEncodingException();
		thrown.expect(NeverGetHereViolation.class);
		thrown.expectCause(sameInstance(t));
		
		neverGetHere(t);
	}

}
