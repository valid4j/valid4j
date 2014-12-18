package org.valid4j.matchers;

import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;

import java.lang.reflect.Modifier;

import static org.valid4j.Assertive.neverGetHereError;

/**
 * Matchers for Class
 */
public class ClassMatchers {

  private ClassMatchers() {
    throw neverGetHereError("Prevent instantiation");
  }

  public static Matcher<? super Class<?>> isAbstractClass() {
    return new CustomTypeSafeMatcher<Class<?>>("is abstract class") {
      @Override
      protected boolean matchesSafely(Class<?> aClass) {
        return Modifier.isAbstract(aClass.getModifiers());
      }
    };
  }
}
