# Packages

## General Info

### Naming
Can be the same as variables, but should rarely include numbers or underscores, and never $

It is best to use all-lowercase package names.

#### Domain naming convensions
It is common to use a reverse-domain name as the package name. For example, if you worked at Google
and were creating an application, you likely would start with the `com.google` package. My packages,
for example, use `com.madimadica` as a start, and sometimes `com.madimadica.learn` for a subdomain.


### Directory Map
Packages map to the same directory as the package name. So package `foo.bar.baz` maps to 
`foo/bar/baz`. Files inside the package must be in the corresponding directory. That is,
if you have `foo.bar.baz.Example` then it should be written in `foo/bar/baz/Example.java`.

### `package` declaration
When creating a file inside a package, the first statement in the file
must be a package declaration matching the current package, such as
```java
package com.madimadica;
```

## Default Package
The default package is included by default and does not require any package declaration.
The main issue with the default package is classes can only be referenced inside
the default package, so no subpackages can reference a default package element.


## Fully Qualified Name
A fully qualified name is the full reference to a given class. It begins with the
package name and then another dot (`.`), and then the class name.

For example, `Scanner` is from the `java.util` package, and its fully qualified name
would be `java.util.Scanner`.

If you do not import a package or class, you can still reference it with the fully qualified name.
For example, if the following code was all that was in a file, it would still work:

```java
public class FullyQualifiedExample {
    public static void main(String[] args) {
        java.util.Scanner in = new java.util.Scanner(System.in);
    }
}
```

You would almost never use this, but sometimes it can be useful if there are naming
collisions, such as `java.awt.List` and `java.util.List`. If there are multiple of 
the same class in the imported context, the ambiguity must be resolved by either
removing the duplicate import, or using the fully qualified name. 



## Importing
In order to reference a class type without the fully qualified name, you can import
the fully qualified name at the start of the file. For example:
```java
import java.util.Scanner;

public class FullyQualifiedExample {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
    }
}
```

Most of the time you should import a class like this.

### Wildcard
Instead of importing a single class at a time from a package,
you can import all of them at once using a *wildcard import*.
A wildcard import uses the asterisk (`*`) instead of the class name.

For example
```java
import java.util.*;

public class FullyQualifiedExample {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        List<String> list = new ArrayList<>();
    }
}
```
here, because we imported with a wildcard, we can resolve the identifiers for 
`Scanner`, `List`, and `ArrayList<>`.

Wildcards can sometimes cause naming collisions, so it is technically
preferable to only import what is necessary. IntelliJ can auto import, and
you can also enable auto import optimization.

#### Subpackages
If you wildcard import a base package, the subpackages are not imported.


### `java.lang`
How can we use `String` or `System.in` without a fully qualified class name or import?
That is because `java.lang` is automatically imported with a wildcard to every java file.
This is because they are very so common, you wouldn't want to import `String`, would you?

You can still write `import java.lang.*;` if you want, but it is completely redundant.

## Static Imports
You can `import static` a fully qualified class name, and then either specify a method,
static constant, or a wildcard.

This is especially useful with junit, because you can
```java
import static org.junit.jupiter.api.Assertions.*;
```
and reference static methods like `assertTrue(x);` without needing to say `Assertions.assertTrue(x)`

This also works with enum, such as `import static com.madimadica.example.Direction.*` and then we could
use `N`, `NE`, `E`, `SE`, etc. identifiers without qualifying the class `Direction.N`.

