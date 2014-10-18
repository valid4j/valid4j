package org.valid4j;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.valid4j.exceptions.EnsureViolation;
import org.valid4j.exceptions.NeverGetHereViolation;
import org.valid4j.exceptions.RequireViolation;
import org.valid4j.fixture.AssertiveMockProvider;

import static org.valid4j.Assertive.*;
import static org.valid4j.fixture.FixtureProviders.CLASS_NAME_OF_CUSTOMIZED_MOCK_PROVIDER;
import static org.valid4j.fixture.FixtureProviders.setProviderProperty;

/**
 * Test behavior when customized provider does not provide assertive policies.
 */
public class AssertiveFallbackBehavior {

  @Rule
  public ExpectedException thrown = ExpectedException.none().handleAssertionErrors();

  @Before
  public void setupCustomizedProviderWithNoPolicies() {
    AssertiveMockProvider.requirePolicy = null;
    AssertiveMockProvider.ensurePolicy = null;
    AssertiveMockProvider.neverGetHerePolicy = null;
    setProviderProperty(CLASS_NAME_OF_CUSTOMIZED_MOCK_PROVIDER);

    Assertive.init();
  }

  @Test
  public void shouldUseFallbackRequirePolicy() {
    thrown.expect(RequireViolation.class);

    require(false, "testing require of fallback policy");
  }

  @Test
  public void shouldUseFallbackEnsurePolicy() {
    thrown.expect(EnsureViolation.class);

    ensure(false, "testing ensure of fallback policy");
  }

  @Test
  public void shouldUseFallbackNeverGetHerePolicy() {
    thrown.expect(NeverGetHereViolation.class);

    neverGetHere("testing never-get-here of fallback policy");
  }

}
