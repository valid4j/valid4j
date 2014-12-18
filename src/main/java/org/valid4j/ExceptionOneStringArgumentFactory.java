package org.valid4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.valid4j.Assertive.neverGetHereError;
import static org.valid4j.Assertive.require;

/**
 * Factory that constructs a recoverable exception given a string argument.
 */
public class ExceptionOneStringArgumentFactory<T extends Exception> implements ExceptionFactory<T> {

  private final Constructor<T> exceptionConstructor;

  public ExceptionOneStringArgumentFactory(Constructor<T> exceptionConstructor) {
		require(exceptionConstructor, notNullValue());
    require(Modifier.isPublic(exceptionConstructor.getModifiers()), "Exception constructor must be public");
    final Class<?>[] parameters = exceptionConstructor.getParameterTypes();
    require(parameters.length == 1, "Exception constructor must accept one and only one argument");
    require(String.class.equals(parameters[0]), "Exception constructor must accept a String argument");
    this.exceptionConstructor = exceptionConstructor;
  }

  @Override
  public T newInstance(String message) {
    try {
      return exceptionConstructor.newInstance(message);
    } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw neverGetHereError(e);
    }
  }
}
