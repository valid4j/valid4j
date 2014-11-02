package org.valid4j;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.valid4j.exceptions.RequireViolation;
import org.valid4j.fixture.AssertiveMockProvider;

import static org.mockito.Mockito.*;
import static org.valid4j.Assertive.*;
import static org.valid4j.fixture.FixtureProviders.*;

/**
 * Test loading a customized assertive provider via service loader.
 */
public class AssertiveServiceLoaderBehavior {

  @Rule
  public ExpectedException thrown = ExpectedException.none().handleAssertionErrors();

  @Before
  public void makeSureAssertiveCanBeLoadedWithDefaultProvider() {
    makeSureAssertiveIsLoadedWithDefaultProvider();
  }

  @AfterClass
  public static void makeSureAssertiveIsLoadedWithDefaultProvider() {
    clearProviderLoader();
    clearProviderProperty();
    Assertive.init();
  }

  @Test
  public void shouldLoadProviderFromServiceLoader() throws Exception {
    AssertiveMockProvider.requirePolicy = mock(AssertivePolicy.class);
    AssertiveMockProvider.ensurePolicy = mock(AssertivePolicy.class);
    AssertiveMockProvider.neverGetHerePolicy = mock(UnreachablePolicy.class);
    setProviderLoader(CLASS_NAME_OF_CUSTOMIZED_MOCK_PROVIDER);
    Assertive.init();

    require(true, "test require policy is loaded from file");
    verify(AssertiveMockProvider.requirePolicy).check(true, "test require policy is loaded from file");

    ensure(true, "test ensure policy is loaded from file");
    verify(AssertiveMockProvider.ensurePolicy).check(true, "test ensure policy is loaded from file");

    neverGetHere("test never get here policy is loaded from file");
    verify(AssertiveMockProvider.neverGetHerePolicy).neverGetHere(null, "test never get here policy is loaded from file");

    verifyNoMoreInteractions(
        AssertiveMockProvider.requirePolicy,
        AssertiveMockProvider.ensurePolicy,
        AssertiveMockProvider.neverGetHerePolicy);
  }

  @Test
  public void shouldLoadDefaultProviderIfTooManyProvidersAreFound() {
    AssertiveMockProvider.requirePolicy = mock(AssertivePolicy.class);
    AssertiveMockProvider.ensurePolicy = mock(AssertivePolicy.class);
    AssertiveMockProvider.neverGetHerePolicy = mock(UnreachablePolicy.class);
    setProviderLoader(CLASS_NAME_OF_CUSTOMIZED_MOCK_PROVIDER +
        "\n" +
        Assertive.DEFAULT_PROVIDER +
        "\n" +
        Assertive.DISABLED_PROVIDER);
    Assertive.init();

    thrown.expect(RequireViolation.class);
    require(false, "test default provider if too many providers are found");
  }
}
