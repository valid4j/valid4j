package org.valid4j.matchers;

import org.hamcrest.StringDescription;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.valid4j.matchers.ArgumentMatchers.isNotEmptyString;
import static org.valid4j.matchers.ArgumentMatchers.isNotNull;

/**
 * Testing the behavior of argument matchers
 */
public class ArgumentMatcherBehavior {

  @Test
  public void shouldMatchNotNullArgument() {
    assertThat(new Object(), isNotNull());
    assertThat(new Object(), isNotNull("myArgument"));
  }

  @Test
  public void shouldNotMatchNullArgument() {
    assertThat(null, not(isNotNull()));
    assertThat(null, not(isNotNull("someArgument")));
  }

  @Test
  public void shouldHaveArgumentDescriptionForNullValue() {
    StringDescription description = new StringDescription();
    isNotNull("my-parameter").describeTo(description);
    assertThat(description.toString(), equalTo("\"my-parameter\" not null"));
  }

  @Test
  public void shouldMatchNotEmptyString() {
    assertThat("some string", isNotEmptyString());
    assertThat("some string", isNotEmptyString("parameterName"));
  }

  @Test
  public void shouldNotMatchEmptyString() {
    assertThat("", not(isNotEmptyString()));
    assertThat("", not(isNotEmptyString("parameterName")));
  }

  @Test
  public void shouldNotMatchNullForEmptyString() {
    assertThat(null, not(isNotEmptyString()));
    assertThat(null, not(isNotEmptyString("parameterName")));
  }

  @Test
  public void shouldHaveArgumentDescriptionForEmptyString() {
    StringDescription description = new StringDescription();
    isNotEmptyString("parameterName").describeTo(description);
    assertThat(description.toString(), equalTo("\"parameterName\" not empty"));
  }
}
