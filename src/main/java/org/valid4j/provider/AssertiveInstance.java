package org.valid4j.provider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import static org.valid4j.Assertive.neverGetHereError;
import static org.valid4j.provider.AssertiveCachingProvider.cached;
import static org.valid4j.provider.AssertiveConstants.*;

/**
 * Instance holder for global AssertiveProvider.
 */
public class AssertiveInstance {

  private static AssertiveProvider provider;

  /**
   * To customize the behavior of Assertive, specify a system property 'org.valid4j.provider.AssertiveProvider'
   * with the class name of the assertive provider to use. Or register the assertive provider in a file
   * on the classpath at 'META-INF/services/org.valid4j.provider.AssertiveProvider' with a single line in it
   * containing the class name of the assertive provider to use.
   */
  static {
    init();
  }

  private AssertiveInstance() {
    throw neverGetHereError("Prevent instantiation");
  }

  public static AssertiveProvider getInstance() {
    return provider;
  }

  static void init() {
    try {
      provider = cached(createProvider());
    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | ClassCastException e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  private static String getProviderName() {
    String providerName = System.getProperty(ASSERTIVE_PROPERTY_NAME);
    return DISABLED.equals(providerName) ? DISABLED_PROVIDER : providerName;
  }

  private static AssertiveProvider createProvider()
      throws IllegalAccessException, InstantiationException, ClassNotFoundException, ClassCastException {

    // Create provider from property (iff a provider name is given)
    final String providerName = getProviderName();
    if (providerName != null && !providerName.isEmpty()) {
      return createProviderForClassName(providerName);
    }

    // Create provider from service loader (iff exactly one provider is found)
    List<AssertiveProvider> providers = new ArrayList<>();
    ServiceLoader<AssertiveProvider> loader = ServiceLoader.load(AssertiveProvider.class);
    Iterator<AssertiveProvider> iterator = loader.iterator();
    while (iterator.hasNext()) {
      providers.add(iterator.next());
    }

    if (providers.size() == 1) {
      return providers.get(0);
    } else if (providers.size() > 1) {
      System.err.println(ASSERTIVE_PROPERTY_NAME + ": Multiple registered providers found in META-INF/services/");
      for (AssertiveProvider provider : providers) {
        System.err.println(ASSERTIVE_PROPERTY_NAME + ": Found " + provider.getClass().getName());
      }
      System.err.println(ASSERTIVE_PROPERTY_NAME + ": Using default provider " + DEFAULT_PROVIDER);
    }

    // Otherwise, return default provider
    return createProviderForClassName(DEFAULT_PROVIDER);
  }

  private static AssertiveProvider createProviderForClassName(String providerName)
      throws ClassNotFoundException, IllegalAccessException, InstantiationException, ClassCastException {
    Class c = Class.forName(providerName);
    return (AssertiveProvider) c.newInstance();
  }

}
