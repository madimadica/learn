---
title: Whitespace
---


**Whitespace characters** are special characters used to represent vertical and horizontal spacing. The most common types are *spaces*, *tabs*, and *newlines*. There are other whitespace types that are rarely used, such as form-feeds, zero-width spaces or vertical tabs - but we will ignore these.

Whitespace characters are special characters used to represent vertical and horizontal spacing. The most common types are spaces, tabs, and newlines. There are other whitespace types that are rarely used, such as form-feeds, zero-width spaces or vertical tabs - but we will ignore these.

## Common Whitespace Types
### Spaces

A **space** is the most common type of whitespace. It is a single width horizontal character, created with the spacebar. An interesting note is that spaces have an ASCII value `32`, or a hexadecimal value of `0x20`. (This is why some web URLs have `%20`, to encode a space.)

### Tabs
A **tab** is the other main type of horizontal whitespace, created with the TAB key. The tab-width depends on the application rendering it. A typical tab-width is 4 spaces, so pressing TAB will go to the *next* multiple of 4. If you are at position 3, tab moves to 4. A tab at position 4 moves to position 8. Some less common tab-widths are 2, 3, and 8.

### Newlines
**Newlines**, also referred to as a *line feed*, create a single line of vertical whitespace. Breaking the text at the current line, and continuing at the start of the next. You can create newlines with the ENTER key.

### Carriage Returns

A **carriage return** is a special control character to move the cursor to the *beginning* of a line. Anything typed *after* it will replace all the text on the current-line, unless it is a newline character. While it is technically considered whitespace, it does not have any visual representation. Carriage returns are usually used in combination with line-feeds on Windows systems.

A practical usage of carriage returns would be in a command-line application to display the current progress of a task, such as a download, and update the progress in real-time (such as `Downloading... 5.6MB / 123.4MB`).

## Java Escape Sequences


You can use whitespaces inside Java Strings. Some whitespaces must be escaped using special *escape sequences* that begin with a backslash. Others can be directly typed in.

* A *space* is typed using the spacebar (or `\s`).
* A *tab* is typed with the TAB key, or using the `\t` escape sequence.
* A *newline* can only be created with the `\n` escape sequence for Strings. In @chapter3, we will learn about *text blocks* to directly type in newlines without `\n`.
* A *carriage return* can only be created with the `\r` escape sequence.

Here are some examples of using each common type of whitespace:
```java
public static void main(String[] args) {
    System.out.println("Hello World"); // Space
    System.out.println("Hello\tWorld"); // Tab
    System.out.println("Hello\nWorld"); // Newline
    System.out.println("Hello\rWorld"); // Carriage Return
    System.out.println("Hello\r\nWorld"); // CRLF (carriage-return line-feed)
}
```

Try running the previous code, commenting off all but one line at a time to see the different outputs. When you run the carriage return line, you should only see `World` in the output. As we just learned, carriage returns move the cursor to the *beginning* of the line, and replaces the contents (unless the next character is `\n`). We will rarely use this fact, but it is nice to be aware of it.

## Line Endings - Windows vs. UNIX
Line endings are used to create a single line of whitespace, but they are not always a simple `\n`. On UNIX systems, newlines *are* just a single `\n`. But, Windows systems use `\r\n` instead. This is sometimes abbreviated as `CRLF` (carriage-return new-line). Sometimes these difference can cause issues when using files across different operating systems, but versioning tools like `git` can automatically convert between them.

Java provides a way to get the correct line ending for the current system with `System.lineSeparator()`. This returns `\r\n` for Windows, and `\n` for UNIX systems. Some methods, such as `System.out.println()`, will automatically use the correct line separator. Most console apps should work fine with `\n` (even on Windows), but processing text files can cause problems.

## Comparing `print` and `println`
Java provides a few methods to print to the standard output. So far, we have only used `System.out.println()`. Now that we know about whitespaces, we will introduce `System.out.print()`. This acts the same way as `System.out.println()`, except it *does not* add a line ending to the output. Recall that `println` stands for *print line*, so `print` simply stands for *print*. Let's see some examples.

```java
// Print "hello" and "world" with a line ending between
System.out.println("hello");
System.out.println("world");

// Use multiple 'print' methods to effectively print "hello world"
System.out.print("he");
System.out.print("llo");
System.out.print(" world");

// Use combinations of both
System.out.print("hello ");
System.out.println("world");
System.out.println("Exiting...");
```

Most of the time, `println()` is what we want. But, sometimes we will use `print()` to keep the cursor on the same line, such as prompting a user for input (starting in @chapter5).

## Whitespace Significance
Generally speaking, whitespace does not matter in Java, but there are some places that do. Some places require *at least* one whitespace (vertical or horizontal). This is usually between *tokens*. A rediculous example *hello world* program could be
```java
// This works... DONT do this.
 public     class
   HorribleExample                   {
      public        static
void     main    (    String  []    args
  )
 {         System  .
      out    . println  (   "hello world"
      )
   ;
      }             }
```
but this is ugly and very difficult to read. So we will use a standard format like we have already been doing.

Something we *can't* do is `publicstaticvoidmain`. It is not obvious where one token starts and the next begins, so the Java compiler fails to parse this properly. (Here, we actually wanted 4 tokens: `public`, `static`, `void`, `main`, but instead we only got one token for `publicstaticvoidmain`.)

One area that whitespace can cause problems is with Strings. Whitespace does matter for Strings, as that is part of the String. "hello world" is different from "helloworld". And as we mentioned with escape sequences, you have to escape newlines in Strings. This means that the following example will not work:

One area that whitespace can cause problems is with Strings. Whitespace *does* matter for Strings, as that is part of the String. `"hello world"` is different from `"helloworld"`. And as we mentioned with escape sequences, you have to escape newlines in Strings. This means that the following example will **not** work:
```java
System.out.println(" 
    1. one
    2. two
    3. three
");
```

This is because String literals must begin and end on the same line. Instead, we can use the newline escape sequences with `System.out.println("1. one\n2. two\n3. three")`;, or wait until we learn how to use text blocks.

Whitespace can also matter for comments. Consider this snippet:
```java
// This is part of the comment
but this it not
System.out.println("hello world");
```
Since a single-line comment only affects one line, the `but this is not` is expected to be code. Since it is not valid code, this snippet would not compile.

## Tabs vs. Spaces
While we are on the topic of whitespace, I will quickly bring up a historical debate. Should you use tabs or spaces (for indenting your code)? This is left as an exercise for the reader. This is mostly an inside joke for programmers, though some people take the debate seriously. Most IDEs can be configured to use spaces/tabs interchangeably, replacing the opposite type based on tab-width.

