package org.valid4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.valid4j.Assertive.neverGetHereError;
import static org.valid4j.Assertive.require;

/**
 * Factory that constructs a recoverable exception given no argument.
 */
public class ExceptionNoArgumentFactory<T extends Exception> implements ExceptionFactory<T> {

  private final Constructor<T> exceptionConstructor;

  public ExceptionNoArgumentFactory(Constructor<T> exceptionConstructor) {
		require(exceptionConstructor, notNullValue());
    require(Modifier.isPublic(exceptionConstructor.getModifiers()), "Exception constructor must be public");
    require(!Modifier.isAbstract(exceptionConstructor.getModifiers()), "Exception class must not be abstract");
    final Class<?>[] parameters = exceptionConstructor.getParameterTypes();
    require(parameters.length == 0, "Exception constructor must accept no argument");
    this.exceptionConstructor = exceptionConstructor;
  }

  @Override
  public T newInstance(String ignoredMessage) {
    try {
      return exceptionConstructor.newInstance();
    } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw neverGetHereError(e);
    }
  }
}
