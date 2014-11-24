valid4j
=======

[![Build Status](https://travis-ci.org/helsing/valid4j.png)](https://travis-ci.org/helsing/valid4j)
[![Coverage Status](https://coveralls.io/repos/helsing/valid4j/badge.png)](https://coveralls.io/r/helsing/valid4j)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.valid4j/valid4j/badge.png)](https://maven-badges.herokuapp.com/maven-central/org.valid4j/valid4j)

A simple validation library for Java which makes it possible to use your 
favorite [hamcrest-matchers](http://hamcrest.org/JavaHamcrest/) to 
express pre- and post-conditions in your code. Use the global default 
policy to signal logical violations in your code or optionally specify 
your own handling. Documentation is available at [www.valid4j.org](http://www.valid4j.org).

This library is available at [Maven Central Repository](http://search.maven.org/).
Add this dependency to your `pom.xml`

    <dependency>
      <groupId>org.valid4j</groupId>
      <artifactId>valid4j</artifactId>
      <version>0.4.0</version>
    </dependency>

Statically import the library entry point:

    import static org.valid4j.Assertive.*;

Use assertive preconditions to check for programming errors in calling clients:

    // Express your preconditions using plain boolean expressions, with a helpful error message (optional)
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

