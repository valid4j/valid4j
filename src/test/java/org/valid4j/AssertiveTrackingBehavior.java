package org.valid4j;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.valid4j.errors.ContractViolation;
import org.valid4j.errors.EnsureViolation;
import org.valid4j.errors.NeverGetHereViolation;
import org.valid4j.errors.RequireViolation;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.valid4j.Assertive.*;
import static org.valid4j.fixture.FixtureProviders.clearProviderProperty;
import static org.valid4j.impl.AssertiveDefaultProvider.getFirstTrackedViolations;

/**
 * Testing behavior of tracking contract violations of the default assertive behavior
 */
public class AssertiveTrackingBehavior {

  @Before
  public void initializeAssertiveAndItsTracker() {
    restoreAssertiveAndItsTracker();
  }

  @AfterClass
  public static void restoreAssertiveAndItsTracker() {
    clearProviderProperty();
    Assertive.init();
  }

  @Test
  public void shouldTrackRequireViolation() {
    requireIgnoreThrow(false, "testing require violation");
    assertThat(getFirstTrackedViolations(), contains(isRequireViolation("testing require violation")));
  }

  @Test
  public void shouldTrackRequireViolations() {
    requireIgnoreThrow(false, "1");
    requireIgnoreThrow(false, "2");
    requireIgnoreThrow(false, "3");
    assertThat(getFirstTrackedViolations(), contains(
        isRequireViolation("1"), isRequireViolation("2"), isRequireViolation("3")));
  }

  @Test
  public void shouldTrackEnsureViolation() {
    ensureIgnoreThrow(false, "1");
    assertThat(getFirstTrackedViolations(), contains(isEnsureViolation("1")));
  }

  @Test
  public void shouldTrackEnsureViolations() {
    ensureIgnoreThrow(false, "2");
    ensureIgnoreThrow(false, "3");
    assertThat(getFirstTrackedViolations(), contains(isEnsureViolation("2"), isEnsureViolation("3")));
  }

  @Test
  public void shouldTrackNeverGetHereViolations() {
    neverGetHereIgnoreThrow("1");
    neverGetHereIgnoreThrow("2");
    neverGetHereIgnoreThrow("3");
    neverGetHereIgnoreThrow("4");
    assertThat(getFirstTrackedViolations(), contains(
        isNeverGetHereViolation("1"),
        isNeverGetHereViolation("2"),
        isNeverGetHereViolation("3"),
        isNeverGetHereViolation("4")));
  }

  @Test
  public void shouldTrackMaxViolations() {
    requireIgnoreThrow(false, "1");
    ensureIgnoreThrow(false, "2");
    neverGetHereIgnoreThrow("3");
    requireIgnoreThrow(false, "4");
    ensureIgnoreThrow(false, "5");
    neverGetHereIgnoreThrow("6");
    requireIgnoreThrow(false, "7");
    ensureIgnoreThrow(false, "8");
    neverGetHereIgnoreThrow("9");
    requireIgnoreThrow(false, "10");
    ensureIgnoreThrow(false, "11");
    neverGetHereIgnoreThrow("12");
    assertThat(getFirstTrackedViolations(), contains(
        isRequireViolation("1"),
        isEnsureViolation("2"),
        isNeverGetHereViolation("3"),
        isRequireViolation("4"),
        isEnsureViolation("5"),
        isNeverGetHereViolation("6"),
        isRequireViolation("7"),
        isEnsureViolation("8"),
        isNeverGetHereViolation("9"),
        isRequireViolation("10")));
  }

  private static Matcher<ContractViolation> isRequireViolation(String msg) {
    return allOf(instanceOf(RequireViolation.class), withMessage(msg));
  }

  private Matcher<ContractViolation> isEnsureViolation(String msg) {
    return allOf(instanceOf(EnsureViolation.class), withMessage(msg));
  }

  private Matcher<ContractViolation> isNeverGetHereViolation(String msg) {
    return allOf(instanceOf(NeverGetHereViolation.class), withMessage(msg));
  }

  private static Matcher<? super Throwable> withMessage(final String msg) {
    return new TypeSafeMatcher<Throwable>() {
      @Override
      protected boolean matchesSafely(Throwable t) {
        return msg.equals(t.getMessage());
      }

      @Override
      public void describeTo(Description description) {
        description.appendText("with message ").appendText(msg);
      }
    };
  }

  private static void requireIgnoreThrow(boolean condition, String message) {
    try {
      require(condition, message);
    } catch (RequireViolation e) {
      // Do nothing
    }
  }

  private static void ensureIgnoreThrow(boolean condition, String message) {
    try {
      ensure(condition, message);
    } catch (EnsureViolation e) {
      // Do nothing
    }
  }

  private void neverGetHereIgnoreThrow(String msg) {
    try {
      neverGetHere(msg);
    } catch (NeverGetHereViolation e) {
      // Do nothing
    }
  }

}
