package org.valid4j;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.*;

public class ErrorMessageBuilderBehavior {
	
	private final ErrorMessageBuilder msg = new ErrorMessageBuilder();

	@Test
	public void shouldBuildMismatchMessage() {
		String m = msg.withMismatchOf(3, not(equalTo(2)));
		assertThat(m, containsString("expected: not <2>"));
		assertThat(m, containsString("was: <3>"));
	}

	@Test
	public void shouldBuildMismatchMessageWithReason() {
		String m = msg.withMessage("This is not %s", "ok");
		assertThat(m, containsString("This is not ok"));
	}

}
