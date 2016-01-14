package org.valid4j;

import org.hamcrest.Matcher;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.valid4j.Assertive.*;
import static org.valid4j.fixture.FixtureProviders.clearProviderProperty;
import static org.valid4j.fixture.FixtureProviders.setProviderProperty;

/**
 * Test behavior of contract violations when checks are disabled.
 */
public class AssertiveDisabledBehavior {

  @AfterClass
  public static void restoreDefaultProvider() {
    clearProviderProperty();
    AssertiveInstance.init();
  }

  private static Matcher<? super Error> defaultError() {
    return nullValue();
  }

  @Before
  public void setupDisabledAssertivePolicyProvider() {
    setProviderProperty(AssertiveConstants.DISABLED);
    AssertiveInstance.init();
  }

  @Test
  public void shouldDoNothingOnRequireContracts() {
    require(true);
    require(false);
    require(false, "disabled");
  }

  @Test
  public void shouldNotInvokeMatchingOnRequireContracts() {
    Object object = mock(Object.class);
    Matcher<?> matcher = mock(Matcher.class);

    require(object, matcher);

    verifyZeroInteractions(object, matcher);
  }

  @Test
  public void shouldDoNothingOnEnsureContracts() {
    ensure(true);
    ensure(false);
    ensure(false, "disabled");
  }

  @Test
  public void shouldNotInvokeMatchingOnEnsureContracts() {
    Object object = mock(Object.class);
    Matcher<?> matcher = mock(Matcher.class);

    ensure(object, matcher);

    verifyZeroInteractions(object, matcher);
  }

  @Test
  public void shouldDoNothingWhenReachingNeverGetHere() {
    Throwable t = mock(Throwable.class);
    String format = "message %s";
    Object value = mock(Object.class);

    neverGetHere();
    neverGetHere("disabled");
    neverGetHere("disabled", value);
    neverGetHere(t);
    neverGetHere(t, format);
    neverGetHere(t, format, value);

    verifyZeroInteractions(t, value);
  }

  @Test
  public void shouldReturnDefaultErrorWhenReachingNeverGetHereError() {
    Throwable t = mock(Throwable.class);
    String format = "message %s";
    Object value = mock(Object.class);

    assertThat(neverGetHereError(), defaultError());
    assertThat(neverGetHereError("disabled"), defaultError());
    assertThat(neverGetHereError("disabled", value), defaultError());
    assertThat(neverGetHereError(t), defaultError());
    assertThat(neverGetHereError(t, format), defaultError());
    assertThat(neverGetHereError(t, format, value), defaultError());

    verifyZeroInteractions(t, value);
  }
}
