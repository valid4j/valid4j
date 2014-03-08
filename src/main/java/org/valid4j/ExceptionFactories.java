package org.valid4j;

import java.lang.reflect.*;

public class ExceptionFactories {

	public static class OneArgumentFactory<T extends RuntimeException> implements ExceptionFactory<T> {

		private final Constructor<T> exceptionConstructor;
		
		public OneArgumentFactory(Constructor<T> exceptionConstructor) {
			if (!Modifier.isPublic(exceptionConstructor.getModifiers())) {
				throw new AssertionError("Exception constructor must be public");
			}
			if (Modifier.isAbstract(exceptionConstructor.getModifiers())) {
				throw new AssertionError("Exception class must not be abstract");
			}
			Class<?>[] parameters = exceptionConstructor.getParameterTypes();
			if (parameters.length != 1) {
				throw new AssertionError("Exception constructor must accept one argument");
			}
			if (!String.class.equals(parameters[0])) {
				throw new AssertionError("Exception constructor must accept one String argument");
			}
			this.exceptionConstructor = exceptionConstructor;
		}

		@Override
		public T newInstance(String msg) {
			try {
				return exceptionConstructor.newInstance(msg);
			} catch (InstantiationException e) {
				throw new AssertionError("Exception class must not be abstract", e);
			} catch (IllegalAccessException e) {
				throw new AssertionError("Exception constructor must be public", e);
			} catch (IllegalArgumentException e) {
				throw new AssertionError("Exception constructor must accept one String argument", e);
			} catch (InvocationTargetException e) {
				throw new AssertionError("The exception constructor must not throw an exception", e);
			}
		}
	}

	public static class NoArgumentFactory<T extends RuntimeException> implements ExceptionFactory<T> {

		private final Constructor<T> exceptionConstructor;
		
		public NoArgumentFactory(Constructor<T> exceptionConstructor) {
			this.exceptionConstructor = exceptionConstructor;
		}

		@Override
		public T newInstance(String msg) {
			try {
				return exceptionConstructor.newInstance();
			} catch (InstantiationException e) {
				throw new AssertionError("Exception class must not be abstract", e);
			} catch (IllegalAccessException e) {
				throw new AssertionError("Exception constructor must be public", e);
			} catch (IllegalArgumentException e) {
				throw new AssertionError("Exception constructor must accept no argument(s)", e);
			} catch (InvocationTargetException e) {
				throw new AssertionError("The exception constructor must not throw an exception", e);
			}
		}
	}

	public static <X extends RuntimeException> ExceptionFactory<X> builder(Class<X> exceptionClass) {
		try {
			return new OneArgumentFactory<X>(exceptionClass.getConstructor(String.class));
		} catch (NoSuchMethodException e) {
			try {
				return new NoArgumentFactory<X>(exceptionClass.getConstructor());
			} catch (NoSuchMethodException e2) {
				throw new AssertionError("Exception class required to have a public constructor accepting one string argument or no arguments. (Note: Inner classes do not satisfy this condition)", e);
			}
		}
	}	
}
