package org.valid4j.matchers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.InvocationTargetException;

import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.valid4j.matchers.ClassMatchers.isAbstractClass;
import static org.valid4j.matchers.ConstructionHelper.tryToInstantiate;
import static org.valid4j.matchers.ConstructorMatchers.classWithPrivateConstructor;
import static org.valid4j.matchers.ExceptionMatchers.preventInstantiationViolation;

/**
 * Testing the behavior of class matchers
 */
public class ClassMatchersBehavior {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  public static abstract class AbstractClass {
  }

  public static class ConcreteClass {
  }

  @Test
  public void shouldMatchAbstractClass() {
    assertThat(AbstractClass.class, isAbstractClass());
  }

  @Test
  public void shouldNotMatchNullForAbstractClass() {
    assertThat(null, not(isAbstractClass()));
  }

  @Test
  public void shouldNotMatchConcreteClassForAbstractClass() {
    assertThat(ConcreteClass.class, not(isAbstractClass()));
  }

  @Test
  public void shouldHavePrivateConstructor() {
    assertThat(ClassMatchers.class, classWithPrivateConstructor());
  }

  @Test
  public void shouldPreventInstantiation() throws Exception {
    thrown.expect(InvocationTargetException.class);
    thrown.expectCause(preventInstantiationViolation());
    tryToInstantiate(ClassMatchers.class);
  }
}
