Overview
========

Valid4J is a simple validation library for Java which makes it possible to use your 
favorite [hamcrest-matchers](http://hamcrest.org/JavaHamcrest/) to express pre- and post-conditions 
in your code, as well as making customized validation. Use the global default policy to signal logical 
violations in your code or optionally specify your own handling.

## Installation

This library is available at [Maven Central Repository](http://search.maven.org/). 
Add this dependency to your `pom.xml`
  
    <dependency>
      <groupId>org.valid4j</groupId>
      <artifactId>valid4j</artifactId>
      <version>0.3.0</version>
    </dependency>

## Getting started

Statically import the library entry point:

    import static org.valid4j.Assertive.*;

Use assertive preconditions to check for programming errors in calling clients:

    // Express your preconditions using plain boolean expressions
    require(v > 0.0, "The value (%f) must be positive", v);
    
    // Or use hamcrest-matchers
    require(v, containsString("great!"));
    
Use assertive postconditions to check for programming errors in your supplied code:

    ensure(result != null);
    ensure(result, greaterThan(3.0));
    
Make use of the convenient pass-through of valid objects:

    // Initialize members with valid arguments
    this.member = require(argument, notNullValue());

    // Return valid results
    return ensure(result, notNullValue());

## Getting started with validation

Statically import the library entry point:

    import static org.valid4j.Validation.*;

Use condition checking to perform simpler error handling and throw recoverable exceptions:

    // Express conditions using plain boolean expressions
    validate(v > 0.0, new NotValidException());
    
    // Or use hamcrest-matchers
    validate(v, containsString("great!"), new MissingGreatException());

## Motivation for valid4j

The rationale behind valid4j (that we think is lacking from comparable alternatives
from e.g. Google Guava, or Apache Commons):

  * provide better support for programming by contract, using pre- and post-conditions
  * similar support for recoverable exceptions, as for programming errors
  * make use of hamcrest library for extensibility
  * make it possible to customize the global policy for contract violations

## And beyond...

  * [In-depth concepts...](./concepts.html)
  * [FAQ](./faq.html)

## Project license

This software is licensed under [Apache Software License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt)
