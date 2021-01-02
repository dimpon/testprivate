[![Maven Central](https://img.shields.io/maven-central/v/io.github.dimpon/testprivate.svg?label=maven%20central&color=green)](https://search.maven.org/search?q=g:%22io.github.dimpon%22%20AND%20a:%22testprivate%22)
[![GitHub Release](https://img.shields.io/github/release/dimpon/testprivate.svg?style=flat&color=green)](https://github.com/dimpon/testprivate/releases)
[![Build Status](https://travis-ci.com/dimpon/testprivate.svg?branch=master)](https://travis-ci.com/dimpon/testprivate)
[![codecov](https://codecov.io/gh/dimpon/testprivate/branch/master/graph/badge.svg)](https://codecov.io/gh/dimpon/testprivate)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)
[![JavaDoc](http://javadoc-badge.appspot.com/io.github.dimpon/testprivate.svg?label=javadoc)](https://javadocio-badges.herokuapp.com/io.github.dimpon/testprivate)


# Library for testing private methods/fields

That is not a secret that developers write Unit Tests for private methods. That is bad, an evidence that something wrong with design, and so on.
But we do it.

For instance, it is vitally important when you refactor legacy code, e.g. with ~1K lines of code classes and cover it with tests.
The really pure evil is changing access of tested method to package-private.
  
Here I offer an alternative solution. Maybe it will make your code cleaner. But better see the sample. 

```xml
<dependency>
    <groupId>io.github.dimpon</groupId>
    <artifactId>testprivate</artifactId>
    <version>0.0.41</version>
    <scope>test</scope>
</dependency>
```

#### Assume that we have a class:
```java
public class ObjectWithPrivates {
    private final AtomicInteger count = new AtomicInteger(0);
    private String name;

    private String methodToTest(String in) {
        return name + in + count.incrementAndGet();
    }
}
```
#### Unit Test:
```java
interface TestPrivates {
    void setName(String name);
    String methodToTest(String in);
    AtomicInteger getCount();
}

@Test
void testPrivates() {
    ObjectWithPrivates o = new ObjectWithPrivates();
    TestPrivates testPrivates = API.lookupPrivatesIn(o).usingInterface(TestPrivates.class);

    testPrivates.setName("Andromeda");
    String in = testPrivates.methodToTest("in");
    AtomicInteger count = testPrivates.getCount();

    Assertions.assertEquals("Andromedain1", in);
    Assertions.assertEquals(1, count.get());
}
```
As you can see, we operate private fields and methods like if they were public. Fields of _o_ are changed.
#### We also can do the same for statics:
```java
public class ObjectWithPrivates {
    private static AtomicInteger count = new AtomicInteger(0);
    private static String name;

    private static String methodToTest(String in) {
        return name + in + count.incrementAndGet();
    }
}
```
#### Unit Test:
```java
interface TestPrivates {
    void setName(String name);
    String methodToTest(String in);
    AtomicInteger getCount();
}

@Test
void testPrivates() {
    TestPrivates testPrivates = API.lookupPrivatesIn(ObjectWithPrivates.class).usingInterface(TestPrivates.class);

    testPrivates.setName("Andromeda");
    String in = testPrivates.methodToTest("in");
    AtomicInteger count = testPrivates.getCount();

    Assertions.assertEquals("Andromedain1", in);
    Assertions.assertEquals(1, count.get());
}
```
#### If a private field or method in superclass:
```java
public class ObjectWithPrivates {
    private  AtomicInteger count = new AtomicInteger(0);
    private  String name;

    private  String methodToTest(String in) {
        return name + in + count.incrementAndGet();
    }
}

public class ObjectWithPrivatesSubclass extends ObjectWithPrivates {
}
```
use _lookupInSuperclass()_ method!
#### Unit Test:
```java
interface TestPrivates {
    void setName(String name);
    String methodToTest(String in);
    AtomicInteger getCount();
}

@Test
void testPrivates() {
    ObjectWithPrivatesSubclass sub = new ObjectWithPrivatesSubclass();
    TestPrivates testPrivates = API.lookupPrivatesIn(sub).lookupInSuperclass().usingInterface(TestPrivates.class);

    testPrivates.setName("Andromeda");
    String in = testPrivates.methodToTest("in");
    AtomicInteger count = testPrivates.getCount();

    Assertions.assertEquals("Andromedain1", in);
    Assertions.assertEquals(1, count.get());
}
```
#### And cherry on the cake - creating objects using private constructors:
```java
public class ClassC {
    private ClassC(){...}
    private ClassC(int a, String b, Object c, Long d) {...}
}

@Test
void createObject() {
    ClassC classC = API.createInstanceOf(ClassC.class).withArguments(new Integer(5), "yo!", new Long(123L), 15L);

    Assertions.assertEquals(5, classC.getA());
    Assertions.assertEquals("yo!", classC.getB());
    Assertions.assertEquals(123L, classC.getC());
    Assertions.assertEquals(15L, classC.getD());
}
```
#### And using private default constructor:
```java
@Test
void createFromPrivateDefault() {
    ClassD u = API.createInstanceOf(ClassD.class).usingDefaultConstructor();
    Assertions.assertTrue(u instanceof ClassD);

    Unsafe unsafe = API.createInstanceOf(Unsafe.class).withArguments();
    Assertions.assertTrue(unsafe instanceof Unsafe);

}
```
Hope it can be useful.

