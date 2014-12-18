package org.valid4j;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertThat;
import static org.valid4j.matchers.ConstructionHelper.tryToInstantiate;
import static org.valid4j.matchers.ConstructorMatchers.classWithPrivateConstructor;
import static org.valid4j.matchers.ExceptionMatchers.preventInstantiationViolation;

public class ValidationBehavior {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void shouldHavePrivateConstructor() {
    assertThat(Validation.class, classWithPrivateConstructor());
  }

  @Test
  public void shouldPreventInstantiation() throws Exception {
    thrown.expect(InvocationTargetException.class);
    thrown.expectCause(preventInstantiationViolation());
    tryToInstantiate(Validation.class);
  }
}
