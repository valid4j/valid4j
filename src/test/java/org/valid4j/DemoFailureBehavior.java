package org.valid4j;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.valid4j.Assertive.require;
import static org.valid4j.Validation.validate;

@Ignore("Run manually for demonstration purposes")
public class DemoFailureBehavior {

  @Test
  public void shouldDescribeFailedVerification() {
    assertThat("This message is bad", containsString("good"));
  }

  @Test
  public void shouldDescribeContractViolation() {
    require("This message is bad", containsString("good"));
  }

  @Test
  public void shouldDescribeFailedInputValidation() {
    validate("This message is bad", containsString("good"), IllegalArgumentException.class);
  }
}
