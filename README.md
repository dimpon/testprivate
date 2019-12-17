
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.dimpon/testprivate/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.dimpon/testprivate)
[![Build Status](https://travis-ci.com/dimpon/testprivate.svg?branch=master)](https://travis-ci.com/dimpon/testprivate)
[![codecov](https://codecov.io/gh/dimpon/testprivate/branch/master/graph/badge.svg)](https://codecov.io/gh/dimpon/testprivate)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)

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
    interface DuplicateString {
        String duplicateString(String in);
    }

    @Test
    void callPrivateMethod() {
        ObjectWithPrivateMethod o = new ObjectWithPrivateMethod();
        DuplicateString duplicateString = instance(o).castTo(DuplicateString.class);
        String one = duplicateString.duplicateString("one");
        Assertions.assertEquals("oneone",one);
    }
```

mvn -DnewVersion=<something> versions:set

versions:set-scm-tag

 mvn -DnewVersion=0.0.29 -DnewTag=v0.0.29 versions:set versions:set-scm-tag
 
 mvn build-helper:parse-version versions:set -DnewVersion=\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.nextIncrementalVersion} -DnewTag=v\${parsedVersion.majorVersion}.\${parsedVersion.minorVersio
 n}.\${parsedVersion.nextIncrementalVersion} versions:set-scm-tag versions:commit
