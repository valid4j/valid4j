package org.valid4j;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.valid4j.fixture.AssertiveMockProvider;

import static org.mockito.Mockito.mock;
import static org.valid4j.fixture.FixtureProviders.*;

/**
 * Test behavior when customized provider does not provide assertive policies.
 */
public class AssertiveFallbackBehavior {

  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  @AfterClass
  public static void restoreDefaultProvider() {
    clearProviderProperty();
    Assertive.init();
  }

  @Test
  public void shouldFailMissingRequirePolicy() {
    AssertiveMockProvider.requirePolicy = null;
    AssertiveMockProvider.ensurePolicy = mock(CheckPolicy.class);
    AssertiveMockProvider.neverGetHerePolicy = mock(UnreachablePolicy.class);
    setProviderProperty(CLASS_NAME_OF_CUSTOMIZED_MOCK_PROVIDER);

    thrown.expect(NullPointerException.class);
    thrown.expectMessage("Missing require policy");
    Assertive.init();
  }

  @Test
  public void shouldFailMissingEnsurePolicy() {
    AssertiveMockProvider.requirePolicy = mock(CheckPolicy.class);
    AssertiveMockProvider.ensurePolicy = null;
    AssertiveMockProvider.neverGetHerePolicy = mock(UnreachablePolicy.class);
    setProviderProperty(CLASS_NAME_OF_CUSTOMIZED_MOCK_PROVIDER);

    thrown.expect(NullPointerException.class);
    thrown.expectMessage("Missing ensure policy");
    Assertive.init();
  }

  @Test
  public void shouldFailMissingNeverGetHerePolicy() {
    AssertiveMockProvider.requirePolicy = mock(CheckPolicy.class);
    AssertiveMockProvider.ensurePolicy = mock(CheckPolicy.class);
    AssertiveMockProvider.neverGetHerePolicy = null;
    setProviderProperty(CLASS_NAME_OF_CUSTOMIZED_MOCK_PROVIDER);

    thrown.expect(NullPointerException.class);
    thrown.expectMessage("Missing never get here policy");
    Assertive.init();
  }
}
