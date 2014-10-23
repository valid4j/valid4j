package org.valid4j;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.valid4j.fixture.AssertiveMockProvider;

import static org.hamcrest.CoreMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.valid4j.Assertive.*;
import static org.valid4j.fixture.FixtureProviders.*;

/**
 * Test customized behavior of contract violations.
 */
public class AssertiveCustomizedBehavior {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void makeSureAssertiveCanBeLoadedWithDefaultProvider() {
    clearProviderProperty();
    Assertive.init();
    AssertiveMockProvider.requirePolicy = mock(AssertivePolicy.class);
    AssertiveMockProvider.ensurePolicy = mock(AssertivePolicy.class);
    AssertiveMockProvider.neverGetHerePolicy = mock(UnreachablePolicy.class);
  }

  @Test
  public void shouldInjectCustomizedAssertivePolicyProvider() {
    setProviderProperty(CLASS_NAME_OF_CUSTOMIZED_MOCK_PROVIDER);

    Assertive.init();
  }

  @Test
  public void shouldUseCustomizedRequirePolicy() {
    setProviderProperty(CLASS_NAME_OF_CUSTOMIZED_MOCK_PROVIDER);
    Assertive.init();

    require(true, "testing require");

    verify(AssertiveMockProvider.requirePolicy).check(true, "testing require");
  }


  @Test
  public void shouldUseCustomizedEnsurePolicy() {
    setProviderProperty(CLASS_NAME_OF_CUSTOMIZED_MOCK_PROVIDER);
    Assertive.init();

    ensure(false, "testing ensure");

    verify(AssertiveMockProvider.ensurePolicy).check(false, "testing ensure");
  }

  @Test
  public void shouldUseCustomizedNeverGetHerePolicy() {
    setProviderProperty(CLASS_NAME_OF_CUSTOMIZED_MOCK_PROVIDER);
    Assertive.init();

    neverGetHere("testing never get here");

    verify(AssertiveMockProvider.neverGetHerePolicy).neverGetHere(null, "testing never get here");
  }

  @Test
  public void shouldFailToInjectNonExistentProvider() {
    setProviderProperty(CLASS_NAME_OF_NON_EXISTENT_PROVIDER);

    thrown.expect(ExceptionInInitializerError.class);
    thrown.expectCause(isA(ClassNotFoundException.class));

    Assertive.init();
  }

  @Test
  public void shouldFailToInjectNonProvider() {
    setProviderProperty(CLASS_NAME_OF_NOT_AN_ASSERTIVE_PROVIDER);

    thrown.expect(ExceptionInInitializerError.class);
    thrown.expectCause(isA(ClassCastException.class));

    Assertive.init();
  }

  @Test
  public void shouldFailToInjectAbstractProvider() {
    setProviderProperty(CLASS_NAME_OF_ABSTRACT_PROVIDER);

    thrown.expect(ExceptionInInitializerError.class);
    thrown.expectCause(isA(InstantiationException.class));

    Assertive.init();
  }

  @Test
  public void shouldFailToInjectProtectedProvider() {
    setProviderProperty(CLASS_NAME_OF_PROTECTED_PROVIDER);

    thrown.expect(ExceptionInInitializerError.class);
    thrown.expectCause(isA(IllegalAccessException.class));

    Assertive.init();
  }
}
