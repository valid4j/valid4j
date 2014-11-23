package org.valid4j.matchers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ConstructionHelper {

  public static <T> T tryToInstantiate(Class<T> c) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    Constructor<?> constructor = c.getDeclaredConstructor();
    constructor.setAccessible(true);
    try {
      return (T)constructor.newInstance();
    } finally {
      constructor.setAccessible(false);
    }
  }

}
