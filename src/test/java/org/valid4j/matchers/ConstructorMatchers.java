package org.valid4j.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class ConstructorMatchers {

  public static Matcher<Class<?>> classWithPrivateConstructor() {
    return new TypeSafeMatcher<Class<?>>() {

      @Override
      protected boolean matchesSafely(Class<?> c) {
        Constructor<?>[] constructors = c.getDeclaredConstructors();
        if (constructors.length != 1) {
          return false;
        }
        return Modifier.isPrivate(constructors[0].getModifiers());
      }

      @Override
      public void describeTo(Description description) {
        description.appendText("class with private constructor");
      }
    };
  }

}
