package org.valid4j.fixture;

import org.valid4j.Assertive;
import org.valid4j.AssertiveCustomizedBehavior;
import org.valid4j.AssertivePolicyProvider;

/**
 * Provider names for unit testing purposes.
 */
public class FixtureProviders {

  public final static String CLASS_NAME_OF_CUSTOMIZED_MOCK_PROVIDER = AssertiveMockProvider.class.getName();
  public final static String CLASS_NAME_OF_NON_EXISTENT_PROVIDER = "org.valid4j.NonExistentProvider";
  public static final String CLASS_NAME_OF_NOT_AN_ASSERTIVE_PROVIDER = AssertiveCustomizedBehavior.class.getName();
  public static final String CLASS_NAME_OF_ABSTRACT_PROVIDER = AssertivePolicyProvider.class.getName();
  public static final String CLASS_NAME_OF_PROTECTED_PROVIDER = AssertiveProtectedProvider.class.getName();

  public static void setProviderProperty(String className) {
    System.setProperty(Assertive.ASSERTIVE_PROPERTY_NAME, className);
  }

  public static String clearProviderProperty() {
    return System.clearProperty(Assertive.ASSERTIVE_PROPERTY_NAME);
  }
}
