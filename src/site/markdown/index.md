Overview
========

Valid4J is a simple validation library for Java which makes it possible to use your 
favorite [hamcrest-matchers](http://hamcrest.org/JavaHamcrest/) to express pre- and post-conditions 
in your code, as well as making customized validation. Use the global default policy to signal logical 
violations in your code or optionally specify your own handling.

Installation
------------

TODO: The library is not yet uploaded to Maven central.
Add this dependency to your `pom.xml`
  
    <dependency>
      <groupId>org.valid4j</groupId>
      <artifactId>valid4j</artifactId>
      <version>TODO</version>
    </dependency>

Getting started
---------------

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

And beyond...
-------------

  * [In-depth concepts...](./concepts.html)
  * [FAQ](./faq.html)

Project license
---------------

  This software is licensed under [Apache Software License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt)
