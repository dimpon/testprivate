package io.github.dimpon.tests;

import io.github.dimpon.ClassC;
import io.github.dimpon.ClassD;
import io.github.dimpon.testprivate.API;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class InstantiationTest {

    @Test
    void createObject() {
        ClassC classC = API.createInstanceOf(ClassC.class).withArguments(new Integer(5), "yo!", new Long(123L), 15L);

        Assertions.assertEquals(5, classC.getA());
        Assertions.assertEquals("yo!", classC.getB());
        Assertions.assertEquals(123L, classC.getC());
        Assertions.assertEquals(15L, classC.getD());

    }

    @Test
    void createFromPrivateDefault() {
        ClassD u = API.createInstanceOf(ClassD.class).withArguments();
        Assertions.assertTrue(u instanceof ClassD);
    }

}
