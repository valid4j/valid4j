package org.valid4j;

import org.hamcrest.*;

public class ErrorMessageBuilder {

	public String withMessage(String formatString, Object... params) {
		return String.format(formatString, params);
	}

	public String withMismatchOf(Object actual, Matcher<?> matcher) {
		return describeMismatchOf(new StringDescription(), actual, matcher).toString();
	}

	public Description describeMismatchOf(Object actual, Matcher<?> matcher) {
		return describeMismatchOf(new StringDescription(), actual, matcher);
	}

	public Description describeMismatchOf(
			Description description, 
			Object actual, 
			Matcher<?> matcher) {
        description.appendText("expected: ")
                   .appendDescriptionOf(matcher)
                   .appendText("\n but was: ")
                   .appendValue(actual);
        return description;
	}

}
