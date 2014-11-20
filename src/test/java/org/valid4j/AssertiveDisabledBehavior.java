package org.valid4j;

import org.hamcrest.Matcher;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.valid4j.exceptions.NeverGetHereViolation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.valid4j.Assertive.*;
import static org.valid4j.fixture.FixtureProviders.clearProviderProperty;
import static org.valid4j.fixture.FixtureProviders.setProviderProperty;

/**
 * Test behavior of contract violations when checks are disabled.
 */
public class AssertiveDisabledBehavior {

  @Before
  public void setupDisabledAssertivePolicyProvider() {
    setProviderProperty(Assertive.DISABLED);
    Assertive.init();
  }

  @AfterClass
  public static void restoreDefaultProvider() {
    clearProviderProperty();
    Assertive.init();
  }

  @Test
  public void shouldDoNothingOnRequireContracts() {
    Object object = mock(Object.class);
    Matcher<?> matcher = mock(Matcher.class);

    require(object, matcher);
    require(true);
    require(false);

    verifyZeroInteractions(object, matcher);
  }

  @Test
  public void shouldDoNothingOnEnsureContracts() {
    Object object = mock(Object.class);
    Matcher<?> matcher = mock(Matcher.class);

    ensure(object, matcher);
    ensure(true);
    ensure(false);

    verifyZeroInteractions(object, matcher);
  }

  @Test
  public void shouldDoNothingWhenReachingNeverGetHere() {
    Throwable t = mock(Throwable.class);
    String format = "message %s";
    Object value = mock(Object.class);

    neverGetHere(t, format, value);

    verifyZeroInteractions(t, value);
  }

  @Ignore
  @Test(expected = NeverGetHereViolation.class)
  public void shouldThrowDefaultErrorWhenReachingNeverGetHere() {
    Throwable t = mock(Throwable.class);
    String format = "message %s";
    Object value = mock(Object.class);

    // TODO: throw neverGetHere(t, format, value);
  }
}
