package org.valid4j;

import java.lang.reflect.Constructor;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.valid4j.Assertive.neverGetHereError;
import static org.valid4j.Assertive.require;

public class ExceptionFactories {

	private ExceptionFactories() {
		throw neverGetHereError("Prevent instantiation");
	}

	public static <X extends Exception> ExceptionFactory<X> exception(Class<X> exceptionClass) {
		require(exceptionClass, notNullValue());
		Constructor<X> constructor = findOneStringArgumentPublicConstructor(exceptionClass);
		if (constructor != null) {
			return new ExceptionOneStringArgumentFactory<X>(constructor);
		} else {
			constructor = findNoArgumentPublicConstructor(exceptionClass);
			if (constructor != null) {
				return new ExceptionNoArgumentFactory<X>(constructor);
			} else {
				throw neverGetHereError("Exception class must have a public constructor accepting one string argument or no arguments. (Note: Inner classes do not satisfy this condition)");
			}
		}
	}

	private static <X extends Exception> Constructor<X> findOneStringArgumentPublicConstructor(Class<X> exceptionClass) {
		Constructor<?> publicConstructors[] = exceptionClass.getConstructors();
		for (Constructor<?> constructor : publicConstructors) {
			if (constructor.getParameterTypes().length != 1) {
				continue;
			}
			if (!constructor.getParameterTypes()[0].equals(String.class)) {
				continue;
			}
			return ((Constructor<X>) constructor);
		}
		return null;
	}

	private static <X extends Exception> Constructor<X> findNoArgumentPublicConstructor(Class<X> exceptionClass) {
		Constructor<?> publicConstructors[] = exceptionClass.getConstructors();
		for (Constructor<?> constructor : publicConstructors) {
			if (constructor.getParameterTypes().length != 0) {
				continue;
			}
			return ((Constructor<X>) constructor);
		}
		return null;
	}

}
