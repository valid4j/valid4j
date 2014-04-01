In-depth
========

# Concepts

Programming by contract is a simple, yet powerful, technique to achieve assertive programming.
The basic idea of valid4j is to make a clear distinction between _programming errors_ and 
_recoverable exceptions_. 
  
## Programming errors

Programming errors are usually the result of an oversight of assumptions that really doesn't hold.
This may result in logical contradictions, like trying to use a non-existent object 
(eg `NullPointerException`) or invoking a method with an incomprehensible argument 
(eg `IllegalArgumentException`).

Logical errors must be corrected at design time. It doesn't make sense to `try` illogical
invocations in order to `catch` and recover from them. But we want our programs to detect, 
expose and locate these bugs.

## Recoverable exceptions

Recoverable exceptions, on the other hand, signal exceptional cases that a correct program can and
should handle gracefully. This might be network connection errors, file system errors or data input
validation errors. This is what we in everyday talk refers to as error handling, fault tolerance and the like.
  
According to the Java exception hierarchy, as a general rule programming errors should inherit from 
`RuntimeException` whereas `Error` is reserved for internal errors of the Java runtime system.
  
However, there is also lots of differing opinions regarding the proper use of checked vs unchecked exceptions.
This has led to the widespread use of `RuntimeException` also for recoverable exceptions.
  
Valid4J prefers to use `Error` to signal programming errors, and is agnostic to the question of
checked vs unchecked recoverable exceptions.

# Defensive programming

...a common, but sad example... 
  
    // Example of client code with sad defensive programming
    Object someParam;
    ...
    Stuff result = supplier.method(someParam);
    if (result == null) {
      // Some error handling
    } else if (supplier.getState() == GREAT) {
      result.doWork();
    } else {
      // Some more error handling
    }


    // Example of supplier code with sad defensive programming
    public Stuff method(Object param) {
      Stuff r;
      if (param == null) {
        // Some error handling
      } else if (getState() == GOOD) {
        r = a.doStuff();
      } else {
        // Even more error handling
      }
      return r;

The characteristic of the above code is that no one knows their responsibilities. No one 
knows what they are supposed to do or what others are supposed to do. Code is recognized 
by "maybe"/"perhaps"/"if you're lucky" statements, obscuring the real semantics
and valid error handling. Are there any programming errors hidden in this code? How do I
know? Defensive programming hides programming errors in a bloat of code and - which is worse - 
are handled as ordinary recoverable errors! Programming errors should not be dealt with this way.
Fail fast, don't let bugs go undetected.

# Contracts

What are your obligations?
What are my obligations?

Principle of design by contract is to clarify the responsibilities by the calling (client) 
side and the corresponding responsibilities of the called (supplier) side. Delegating 
responsibilities is key to all good design. We do this by setting up which conditions must 
be met for the cooperation between client and supplier.

A violation of the precondition means that the client has not fulfilled the contract, hence 
the logical error lies on the client side. The client is _required_ to make sure the 
preconditions hold before calling a method.

A violation of the postcondition means that the supplier has not fulfilled the contract, 
hence the logical error lies on the supplier side. The supplier _ensures_ that the 
postcondition holds before returning from a method.

By using the mechanism of contracts, bugs in the software are instantly exposed as soon as 
they are detected in runtime. A contract violation means that an assumption in the 
software doesn’t hold. This means we are executing in an unknown state. The only reasonable 
response is to abort the application (possibly followed with some kind of global recovery,
like restarting the application, returning a HTTP 500 response or similar). The alternative 
would have been to ignore the violation, continue running the software in an unknown state 
and hence producing unknown results. This is not acceptable.

# Assertive programming

Using contracts we clarify the responsibilities between the calling side (client) and 
the called side (supplier). It's easy to see what conditions we can rely on and which 
conditions we are to achieve, and also who is responsible to achieve those conditions 
(client or supplier). The readability of the code is improved. No cluttering with 
unnecessary if-else statements. Code to the point.

Contracts are enforced at runtime and will be checked whenever this code is executed. 
Any bugs in the software have greater probability to be detected, exposed, found and 
eventually fixed! 

Contracts help out when debugging. Passed contracts shows what conditions are true. 
"Don’t assume – test!"

    // Example of assertive client code
    Stuff result = supplier.method(someParam);
    result.doWork();

    // Example of assertive supplier code
    public Stuff method(Object param) {
      require(param, notNullValue());
      require(getState(), equalTo(GOOD));
      ...
      ensure(getState(), equalTo(GREAT));
      return ensure(r, notNullValue());
    }

A contract violation implies a logical error in the program. Therefore it doesn't
make sense to try to recover from it. If you do, you're doing it wrong...

If a bug is detected in your application, it means it is in an unknown state, a state 
that your code is not prepared to handle. A reasonable _global_ recovery policy would perhaps
be to restart your application, return a HTTP 500 response or similar.

Beneficial side effects of using contracts is that it
• encourages the use of a single exit/return statement where the ensure contract 
  may be checked. Several return statements each checking the contract would 
  clutter the code and be error prone.
• makes it obvious for the programmer to reason about what recoverable exceptions 
  could occur when executing the code, i.e. the cases when the ensure contracts 
  cannot be fulfilled.

## Require contracts (preconditions)

* The clients' responsibility. What the client is _required_ to fulfill.
* Expresses what has to be fulfilled before a method may be called.
* Conditions on the state of the supplier, and/or input parameters.

## Ensure contracts (postconditions)

* The suppliers' responsibility. What the supplier _ensures_ will be fulfilled.
* Expresses what is fulfilled after the method has executed.
* Conditions on the state of the supplier, and/or return values.
* Recoverable exception(s) may be thrown in those cases where the method can not
  fulfill its postcondition(s). (This is error handling, not contracts...)
  
## Invariants

* Expresses what is always fulfilled for an objects.
* **Important**: Must hold _even if_ an exception is thrown!
* This is trickier that it seems... Be careful - dragons be here!

# Invariants

Dragons be here...
Prefer immutable classes and check invariants at exit of constructor. This 
is a much simpler special case of invariants.

# Good manners to keep

If you are using contracts to clarify the responsibilities between clients and suppliers 
there are a couple of things to keep in mind. 

## Separate queries from commands

There are two types of methods in the world:

* Commands/mutators - which change the program state in any way.
* Queries/accessors - which do not change the program state. These are side-effect free functions. 

You should separate commands from queries. Your methods should have a single 
well-defined responsibility. Either they are commands, methods that change 
the current state of your program. Or they are queries, side-effect free methods 
that just return a result. Contract checking shall not change the (perceived) 
program state. Perceived that is, like acquiring & releasing a mutex is ok, caching is ok. 

Why? Goes without saying! Contracts are a part of the specification, not the semantics. 
The outcome of a contract check must not depend on how many times it has been 
checked (if any).

Evaluating your contracts must be a side-effect free function. Think of contracts as assertions. 
Your program should execute correctly even if your contracts aren't checked at all at runtime. 
Therefore it is important that no state-altering methods (i.e. commands) are invoked when 
evaluating contract conditions.

Only use queries in contract conditions. Never use commands in contracts.

## Provide a complete interface for your clients

Specifying preconditions is the same as putting requirements on your clients. You are
essentially demanding them to fulfill their responsibilities. Make sure they also
have the possibility to achieve that.

    public class BadManners {
      public BadManners(int i) {
        require(isValid(i)); // Using private methods in contracts...
                             // ...makes the class pretty useless
                             // DON'T DO THIS!
      }
      private static boolean isValid(int i) {
        ...
      }
    }

When specifying your contracts, provide your clients with a public interface to make it
possible for them to fulfill their obligations.

## Don't use contracts for error handling. Don't use error handling for contracts.

Example country codes? Error handling at system boundaries. Never catch and try to recover from contract 
violations - you're doing it wrong if you do. Instead use the uncaughtExceptionHandler for global recovery.
Do not use contracts for error handling!
Contracts ensure the correctness of a program.

Recoverable exceptions are used for fault tolerance and error handling. Contracts are no 
substitute for proper error handling and recovery.

The difference is all about specification. How much responsibility can we put on 
our clients? Contracts are not an input checking tool.

Compare with having external ”systems” as users, networks, hard disk drives, etc.
”External” in this context means outside of our thread of control.

    public void doSomething() throws CouldNotDoSomething {
      require(isGood());
      
      if (tryStuff()) {
        this.state = GREAT;
      } else {
        throw CouldNotDoSomething();
      }
      
      ensure(isGreat());
    }

A rule of thumb:

Anticipated or foreseeable input shall be handled by fault tolerant code, eg user 
input or all kind of data which originates from an external source.

Anticipated or foreseeable error conditions shall be handled by fault tolerance 
code, eg filesystem full or all kind of conditions that originates from an external 
source.

# Contracts and inheritence

LSP. Preconditions may be weakened for subclasses. Postconditions may be strengthened for subclasses. 
(it's not as easy as to say that contracts can simply be inherited by subclasses)

* It must always be possible to replace a base-class with any sub-class.
* Think Liskovs Substitution Principle (LSP). SubClass is-a BaseClass.
* Subclasses may have weaker preconditions, but not stronger.
* Subclasses may have stronger postconditions, but not weaker.

It's alright for subclasses to require less from its client than the baseclass.
It's alright for subclasses to ensure more to its clients than the baseclass.

Subclasses must not require more from their clients than the baseclass.
Subclasses must not ensure less to its clients than the baseclass.

# Contracts and multi-threading

You can't put contracts on conditions that are outside your thread of control. 
Those conditions need to be dealt with using regular error handling, eg throw 
a recoverable exception.

E.g if a class is used by several threads (synchronized) you can't put preconditions
on the supplier _state_! `require(getState() == DO_NOT_DO_THIS);`
This would be an error and you should use proper error handling instead.
`if (!isGood()) { throw new NotGoodException(); }`

You could require the client to first use explicit locls though. If so, put contract
on this!

    require(Thread.holdsLock(this));
    require(getState() == GOOD);
    
Contracts and multiple threads demand special attention! (The same reasoning 
applies to multiple processes as well) Preconditions on input arguments is the 
same in a multi-threaded environment as in a single-threaded environment 
(assuming they themselves aren’t modified by multiple threads...) In order to 
put preconditions on object state in a multi-threaded environment, then the 
client needs to have exclusive access to the object during method execution. 
This is the same thing as first requiring the client to hold a lock for the 
object, and then check for the object state. If so, put contracts on this! 
Compare also with error handling: You can’t put contracts on conditions that 
are outside your thread of control. Those conditions need to be error handled 
instead.

Note: Or you could just try to avoid shared mutable state in multi-threaded environments...
  
# Contracts and unit testing

Contracts and unit testing complement each other. Leave the assertions on in production.

Contracts:
* Capture the general semantics/requirements, eg addition: result == a+b
* Written into the production code
* Enforced during live execution
* May be used to exclude what isn't unit tested

Unit testing:
* Verification of specific execution paths, eg test1: 3 == 1+2
* Example of usage in non-production code
* Exercise the code in a safe environment


Contracts and unit testing complement each other - Eg, the unittest is responsible 
for the execution of a specific path, contracts are responsible for the test 
aspect => less need too open-up access modifier just to be able to execute. 
The contracts are checked during the execution. Contracts clarify the intention of 
the code by explicitly state what we can assume is true about the world. 
You could use contracts to effectively exclude test space that is out of 
specification.

# Skipping contracts

What if the contract isn’t there? I’ll get a NullPointerException, 
isn’t that enough? The concept of contracts is about delegating 
responsibilities and communicating the intent. If you get a 
NullPointerException, you have to ask yourself ”where is the bug?” 
Is this the clients responsibility to pass in an actual object? 
Or is it the suppliers responsibility to handle this case, perhaps
using some default instead of failing?

The contracts state who will be responsible for a certain condition 
to be fulfilled, and indirectly locates any bugs. Consistently apply
contracts and any violations will immediately single out the offending
code.

# Alternatives

Google Guava, commons-lang Validate and Objects.requireNotNull.
  
# Remember

Contracts help us detect, expose, find and fix bugs!

* Detect – contracts violations are detected in production runtime. First contract violation. 
* Expose – a consistent handling of contract violations helps in exposing any bugs. 
  Common handling to throw an unrecoverable error. This kind of error shall never be 
  caught and handled (except possibly at the top level for a global recovery strategy, 
  of course. Make use of uncaughtExceptionHandler) 
* Find – contracts helps in localizing the bugs. At the source, in the client or 
  the supplier? 
* Fix – contracts communicates the assumptions that revolve around a piece of code 
  and hence make it easier to fix any bugs.

# One-liners

* Separate queries from commands
* Only use side-effect free query methods when specifying contracts
* Always make it possible for your clients to live up to their obligations (no hidden requires)
* Do not try to catch and recover from contract violations (only at the top level is it relevant)

---------------
  
# References

  * [Wikipedia, Design by Contract](https://en.wikipedia.org/wiki/Design_by_Contract).
  * [Object-Oriented Software Construction, Chapter 11, Design by Contract](http://www.academia.edu/4903777/Object-Oriented_Software_Construction_SECOND_EDITION).
  * The Pragmatic Programmer, Chapter 4, Design by Contract & Assertive Programming.
  * Google.
