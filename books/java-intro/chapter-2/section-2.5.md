---
title: Case Sensitivity
---
Java in **case-sensitive**, meaning uppercase and lowercase letters mean different things. `System` is not the same as `system`. As such, `system.out.println()` will not compile. This applies to all the tokens. None of the following statements will compile:

```java
SYSTEM.out.println("hello world"); 
System.OUT.println("hello world"); 
System.out.printLine("hello world"); 
```

Note that not all programming languages are case-sensitive, but most are. Everything in Java is case-sensitive. This is actually good because it reduces naming collisions. For example, `Foo` is different from `foo`. There are conventions for how to name things to keep code consistent, and we will review these in a few chapters.

## `main`
Recall that `public static void main(String[] args)` is your program's entry point. Since everything is case-sensitive, the following will is *not* considered the main method - but it will compile:
```java
public static void Main(String[] args) {
    // NOT the correct 'main' method
}
```

Something that will not even compile, even though `main` is lowercase, is
```java
Public STATIC vOID main(String[] args) {

}
```
because all keywords, like `public`, `static`, and `void` are case-sensitive (everything is).
