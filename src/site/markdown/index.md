Overview
========

valid4j is a simple assertion and validation library for Java which makes it possible to use your
favorite [hamcrest-matchers](http://hamcrest.org/JavaHamcrest/) to express pre- and post-conditions 
in your code in a [design by contract](http://en.wikipedia.org/wiki/Design_by_contract) style.

valid4j provides you with an option to customize the global assertion policy by implementing an
`org.valid4j.AssertiveProvider` and register it as a service loader in `META-INF/services`.

valid4j also provides support for regular input validation using [hamcrest-matchers](http://hamcrest.org/JavaHamcrest/)
throwing custom recoverable exceptions if validation fails.

More info [found here](./concepts.html).

## Installation

This library is available at [Maven Central Repository](http://search.maven.org/). 
Add this dependency to your `pom.xml`
  
    <dependency>
      <groupId>org.valid4j</groupId>
      <artifactId>valid4j</artifactId>
      <version>0.4.0</version>
    </dependency>

## Getting started

Statically import the library entry point:

    import static org.valid4j.Assertive.*;

Use assertive preconditions to check for programming errors in calling clients:

    // Use hamcrest-matchers to specify your preconditions
    require(list, everyItem(greaterThanOrEqualTo(3)));

    // Or express your preconditions using plain boolean expressions
    require(v > 0.0, "The value (%f) must be positive", v);

Use assertive postconditions to check for programming errors in your supplied code:

    ensure(result != null);
    ensure(result, greaterThan(3.0));
    
Make use of the convenient pass-through of valid objects:

    // Initialize members with valid arguments
    this.message = require(message, containsString("Greetings"));

    // Return valid results
    return ensure(list, hasSize(greaterThan(1)));

Clearly express what you assume is unreachable code:

    neverGetHere("This should never happen");

    // If needed, give the compiler a hint that this branch won't return
    throw neverGetHereError("Really...!?");

More info [found here](./concepts.html).

## Validation

Statically import the library entry point:

    import static org.valid4j.Validation.*;

Use condition checking to perform simpler error handling and throw recoverable exceptions:

    // Use expressive hamcrest-matchers
    validate(v, containsString("Greetings"), otherwiseThrowing(MissingGreatException.class));

    // Or use plain boolean expressions
    validate(v > 0.0, otherwiseThrowing(NotAPositiveNumberException.class));

More info [found here](./concepts.html).

## Motivation for valid4j

The rationale behind valid4j (that we think is lacking from comparable alternatives
from e.g. Google Guava, or Apache Commons):

  * provide better support for [design by contract](http://en.wikipedia.org/wiki/Design_by_contract), using pre- and post-conditions
  * similar support for recoverable exceptions, as for programming errors
  * make use of hamcrest library for extensibility
  * make it possible to customize the global policy for contract violations

## Project license

This software is licensed under [Apache Software License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt)
