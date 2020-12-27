package io.github.dimpon.subclass;

import io.github.dimpon.testprivate.API;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class ObjectWithPrivatesTest {

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
}
