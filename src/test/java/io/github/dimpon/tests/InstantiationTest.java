package io.github.dimpon.tests;

import io.github.dimpon.ClassC;
import io.github.dimpon.ClassD;
import io.github.dimpon.testprivate.API;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;


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
    void createObjectWithBooleans() {
        ClassC classC = API.createInstanceOf(ClassC.class).withArguments(true, Boolean.TRUE, true, true);
        Assertions.assertEquals("truetruetruetrue", classC.result);
    }

    @Test
    void createObjectWithBytes() {
        byte a = 0b0010_0101;
        Byte b = new Byte(a);
        byte c = 0b0010_0101;
        byte d = 0b0010_0101;
        ClassC classC = API.createInstanceOf(ClassC.class).withArguments(a,b,c,d);
        Assertions.assertEquals("37373737", classC.result);
    }

    @Test
    void createObjectWithShort() {
        short a = 0b0010_0101;
        Short b = new Short(a);
        short c = 0b0010_0101;
        short d = 0b0010_0101;
        ClassC classC = API.createInstanceOf(ClassC.class).withArguments(a,b,c,d);
        Assertions.assertEquals("37373737", classC.result);
    }

    @Test
    void createFromPrivateDefault() {
        ClassD u = API.createInstanceOf(ClassD.class).withArguments();
        Assertions.assertTrue(u instanceof ClassD);
    }

    @Test
    void createUnsafe() {
        Unsafe unsafe = API.createInstanceOf(Unsafe.class).withArguments();
        Assertions.assertTrue(unsafe instanceof Unsafe);
    }
}
