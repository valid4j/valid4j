package org.valid4j.fixture;

import org.valid4j.AssertiveConstants;
import org.valid4j.AssertiveCustomizedBehavior;
import org.valid4j.AssertiveProvider;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Provider names for unit testing purposes.
 */
public class FixtureProviders {

  public final static String CLASS_NAME_OF_CUSTOMIZED_MOCK_PROVIDER = AssertiveMockProvider.class.getName();
  public final static String CLASS_NAME_OF_NON_EXISTENT_PROVIDER = "org.valid4j.NonExistentProvider";
  public static final String CLASS_NAME_OF_NOT_AN_ASSERTIVE_PROVIDER = AssertiveCustomizedBehavior.class.getName();
  public static final String CLASS_NAME_OF_ABSTRACT_PROVIDER = AssertiveProvider.class.getName();
  public static final String CLASS_NAME_OF_PROTECTED_PROVIDER = AssertiveProtectedProvider.class.getName();

  public static void setProviderProperty(String className) {
    System.setProperty(AssertiveConstants.ASSERTIVE_PROPERTY_NAME, className);
  }

  public static String clearProviderProperty() {
    return System.clearProperty(AssertiveConstants.ASSERTIVE_PROPERTY_NAME);
  }

  public static void setProviderLoader(String providerName) {
    try {
      Files.createDirectories(getServicesPath());
      Path assertiveProviderPath = getAssertiveProviderPath();
      try (Writer writer = new FileWriter(assertiveProviderPath.toFile())) {
        writer.write(providerName);
        writer.flush();
      }
    } catch (IOException e) {
      throw new AssertionError("Could not create provider file", e);
    }
  }

  public static void clearProviderLoader() {
    Path assertiveProviderPath = getAssertiveProviderPath();
    try {
      Files.deleteIfExists(assertiveProviderPath);
    } catch (IOException e) {
      throw new AssertionError("Could not delete provider file", e);
    }
  }

  private static Path getServicesPath() {
    try {
      Path rootPath = Paths.get(ClassLoader.getSystemResource("").toURI());
      return rootPath.resolve("META-INF/services");
    } catch (URISyntaxException e) {
      throw new AssertionError("Invalid URI syntax", e);
    }
  }

  private static Path getAssertiveProviderPath() {
    return getServicesPath().resolve(AssertiveProvider.class.getName());
  }
}
