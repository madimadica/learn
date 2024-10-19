---
title: Entry Point (main)
header: Java Program Entry Point (`main`)
---

In order for a Java program to be *executable*, it must have a main method. We have already seen this a few times. It looks like:
```java
public static void main(String[] args) {
    
}
```
Excluding some whitespaces, and a few exceptions related to `(String[] args)`, main must be written *exactly* as above. By the middle of the book we will understand what each part of it means, but until then we can just ignore it.

The only thing we need to know, and remember for the rest of time, is that `public static void main(String[] args)` is where the program *starts*. IntelliJ makes it easy to click the green play-button on the left gutter to run it. A program can have multiple main methods, but only one per file. There is usually only 1 main method per program. Some programs have 0 main methods, but that is generally for a library, such as the built-in Java API (things like `System.out.println()`). For the vast majority of our programs, we will have a main method.

## Useful Shortcuts

You do not need to worry about trying to memorize the main method. You will see it so much while learning to code that it will be ingrained in your brain. While typing it all out helps to remember it, it becomes a chore once you know it. Fortunately, IntelliJ provides a shortcut out-of-the-box to create a main method. Inside the class curly braces, type `main` and press **TAB**. This will generate the boilerplate for you.

Another IntelliJ shortcut is `sout` + **TAB**. This will generate `System.out.println()`, which you will certainly get tired of typing. If you are on Eclipse, `sysout` + **CTRL** + **SPACE** does this too.

## Exit Codes

When your program executes successfully (by exiting the main method), you should see:
```
Process finished with exit code 0
```
An exit code of `0` is successful, and anything else (non-zero) indicates an error (typically `1`). While it is generally bad practice, you can force an exit code with `System.exit(0);` (or an integer of your choice).
