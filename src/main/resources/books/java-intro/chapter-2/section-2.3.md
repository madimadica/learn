---
title: Comments
---

**Comments** are sections of plain-text used to explain code and do not affect the program. Anything written inside a comment is ignored by the compiler and will *never* be executed. Java has two ways to make comments. While the naming can vary, they are usually called *single-line* and *multi-line* comments.

## Single-Line Comments

A **single-line comment**, sometimes called *inline comment* or *end-of-line comment*, applies to only one line, beginning with two forward slashes: `//`. *All* text to the right of the `//` is ignored. Single-line comments end with the end of the line. It can appear on a line by itself, or at the end of a line after some code.

For example,
```java
public static void main(String[] args) {
    System.out.println("hello world"); // output-text
    System.out.println("goodbye"); // output more text
}
```

In the code above, both `// output text` and `// output more text` are comments. The output is `hello world` and `goodbye` on two separate lines.

If the code was instead:

```java
public static void main(String[] args) {
    // System.out.println("hello world");
    System.out.println("goodbye");
}
```

Here, only `goodbye` would be printed, as the `hello world` line is commented off. This is because all code to the *right* of the `//` is ignored. Code is *commented-off* when it could be executed, but won't because it's part of a comment. You may occasionally do this when debugging to temporarily "remove" the code, and can *uncomment* it later.

## Multi-Line Comments
A **Multi-Line comment**, sometimes called a *block comment*, can span multiple lines and begins with a `/*` and ends with `*/`. Everything between the `/*` and `*/` is ignored. Generally when spanning multiple lines, redundant asterisks are used to line up the text.

```java
public static void main(String[] args) {
    /*
     * This is a multi-line (block) comment
     *
     * Nothing in here is executed
     * System.out.println("this won't print");
     */
    System.out.println("this will print");

    /*
        This is also a multi-line (block) comment.
        Asterisks (*) are not needed in the middle
        They are only needed at the start and the very end.

        Without them, it is harder to figure out where to line
        something up.
     */
    System.out.println("this will also print");
}
```

It is also possible to comment off a specific section of a single line. This is considered bad practice since it can be very difficult to red, such as the following comment examples:

```java
public static void main(String[] args) {
    /* bad comment */ System.out.println("this will print");
    System.out.println(/* bad comment */ "this will also print");
}
```

You will sometimes see a "multi-line comment" being used as a "single-line comment":

```java
public static void main(String[] args) {
    /* This is a comment */
    System.out.println("this will print");
}
```

## Javadoc Comments

There is a special type of multi-line comment, called a Javadoc comment. A **Javadoc comment** is a specifically formatted multi-line comment used for documentation. The `javadoc` tool can generate HTML documentation from the comments. As with all other comments, these are ignored by Java's execution, but they are used by the javadoc tool.

Javadoc comments start with `/**`, and end with `*/`.

An example of the generated HTML documentation can be found [here](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/PrintStream.html).

An example javadoc comment, with the code removed for brevity, is shown below:

```java
    /**
     * Prints a String and then terminates the line.  This method behaves as
     * though it invokes {@link #print(String)} and then
     * {@link #print()}.
     *
     * @param x  The {@code String} to be printed.
     */
    public void println(String x) {
        ...
    }
```

Many of the features you can do with javadoc are beyond the scope of this chapter. We will revisit them in %chapter #%.


## TODO Comments
A **TODO comment** indicates that you will come back to fix that code later. It is created by adding `TODO` (case-insensitive) inside any other type of comment. These are quite handy when you're already in the middle of developing a feature and realize something else that needs fixed, but you don't have the time to fix it *right now*. Once it is fixed, delete the TODO comment.

For example, you might add this TODO comment:
```java
public static void main(String[] args) {
    // TODO sort them alphabetically
    System.out.println("Bob, Alice");
}
```

## Viewing TODOs in IntelliJ
Many editors have a way to view all the TODO comments. With the default IntelliJ layout, TODO comments can be found in a tab on the bottom-left of the window. The following image shows an example.

![Enabling Hidden Items in Windows](./todo_ij.png)

## IntelliJ Shortcuts
To comment (or uncomment) the current line, press `CTRL` + `/`. This moves the caret down a line.

To comment (or uncomment) multiple lines, highlight the region, then press `CTRL` + `/`.

## Good Practices
* Add a space after the start of the comment to improve readability.
  * `// Good comment`
  * `//Bad comment`
* Generally avoid using single-line comments at the end-of-line. These can cause code run-off the right of the screen, and nobody wants to use horizontal scrolling. These are sometimes used for short snippets in example code, but not practical for "real" code.
    ```java
    // A good comment location
    System.out.println("goodbye"); // Not a very good comment location
    ```
* Don't over-comment. Once you learn the fundementals, most code does not need to be explained. Too many comments can actually make code *harder* to read.
