[![GitHub Release](https://img.shields.io/github/release/dimpon/testprivate.svg?style=flat)](https://github.com/dimpon/testprivate/releases)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.dimpon/testprivate/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.dimpon/testprivate)
[![Build Status](https://travis-ci.com/dimpon/testprivate.svg?branch=master)](https://travis-ci.com/dimpon/testprivate)
[![codecov](https://codecov.io/gh/dimpon/testprivate/branch/master/graph/badge.svg)](https://codecov.io/gh/dimpon/testprivate)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)
[![JavaDoc](http://javadoc-badge.appspot.com/io.github.dimpon/testprivate.svg?label=javadoc)](https://javadocio-badges.herokuapp.com/io.github.dimpon/testprivate)


# Library for testing private methods

That is not a secret that inspite of recomemdations to avoid it depelopers write Unit Tests for private methods.
It is vitaly important when you refactor legacy code, e.g. with ~1K lines of code classes and cover it with tests.
Basically there are 3 approaches:  
1. Change access of tested method to package-private.
2. Use reflection or some utility classes e.g. Whitebox from Powermock
3. Use code generation on the fly - e.g. Mockito, Powermock

Here the alternative approach is suggested. See the folloging sample. 
Assume that we have class `ObjectWithPrivateMethod` and it has `private String duplicateString(String in)`.  
The Unit Test for the private method:
```java
    
static public class ObjectWithPrivate {
    private String name;

    private String methodToTest(String in) {
        return in + in;
    }
}

//test private method
interface TestPrivateMethod {
    String methodToTest(String in);
}


@Test
void callPrivateMethod() {
     ObjectWithPrivate obj = new ObjectWithPrivate();
     TestPrivateMethod castedObj = lookupPrivatesIn(obj).usingInterface(TestPrivateMethod.class);
     String result = castedObj.methodToTest("one");
     Assertions.assertEquals("oneone", result);
}

//test private field
interface TestPrivateField {
    void setName(String name);
    String getName();
}

@Test
void callPrivateField() {
    ObjectWithPrivate obj = new ObjectWithPrivate();
    TestPrivateField castedObj = lookupPrivatesIn(obj).usingInterface(TestPrivateField.class);
    castedObj.setName("Vasya Pupkin");
    Assertions.assertEquals("Vasya Pupkin", castedObj.getName());
}
    
```

