package org.valid4j;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.valid4j.errors.RequireViolation;
import org.valid4j.fixture.AssertiveMockProvider;

import static org.mockito.Mockito.*;
import static org.valid4j.Assertive.*;
import static org.valid4j.fixture.FixtureProviders.*;

/**
 * Test loading a customized assertive provider via service loader.
 */
public class AssertiveServiceLoaderBehavior {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @AfterClass
  public static void makeSureAssertiveIsLoadedWithDefaultProvider() {
    clearProviderLoader();
    clearProviderProperty();
    AssertiveInstance.init();
  }

  @Before
  public void makeSureAssertiveCanBeLoadedWithDefaultProvider() {
    makeSureAssertiveIsLoadedWithDefaultProvider();
  }

  @Test
  public void shouldLoadProviderFromServiceLoader() throws Exception {
    AssertiveMockProvider.requirePolicy = mock(CheckPolicy.class);
    AssertiveMockProvider.ensurePolicy = mock(CheckPolicy.class);
    AssertiveMockProvider.neverGetHerePolicy = mock(UnreachablePolicy.class);
    setProviderLoader(CLASS_NAME_OF_CUSTOMIZED_MOCK_PROVIDER);
    AssertiveInstance.init();

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
    AssertiveMockProvider.requirePolicy = mock(CheckPolicy.class);
    AssertiveMockProvider.ensurePolicy = mock(CheckPolicy.class);
    AssertiveMockProvider.neverGetHerePolicy = mock(UnreachablePolicy.class);
    setProviderLoader(CLASS_NAME_OF_CUSTOMIZED_MOCK_PROVIDER +
        "\n" +
        AssertiveConstants.DEFAULT_PROVIDER +
        "\n" +
        AssertiveConstants.DISABLED_PROVIDER);
    AssertiveInstance.init();

    thrown.expect(RequireViolation.class);
    require(false, "test default provider if too many providers are found");
  }
}
