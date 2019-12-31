package io.github.dimpon.testprivate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.github.dimpon.testprivate.API.cast;


public class TestprivateTest {

    enum Pole {
        Nord, South
    }

    interface DuplicateString {
        String duplicateString(String in);
    }

    interface TriplicateString {
        String triplicateString(String in);
    }

    interface ToLowerCase {
        String nameInLowerCase();
    }

    @Test
    void callPrivateMethod() {
        ObjectWithPrivateMethod o = new ObjectWithPrivateMethod();
        DuplicateString duplicateString = cast(o).toInterface(DuplicateString.class);
        String one = duplicateString.duplicateString("one");
        Assertions.assertEquals("oneone", one);
    }

    @Test
    void callPrivateEnumMethod() {
        EnumWithPrivateMethods e = EnumWithPrivateMethods.ONE;
        ToLowerCase toLowerCase = cast(e).toInterface(ToLowerCase.class);
        String one = toLowerCase.nameInLowerCase();
        Assertions.assertEquals("one", one);
    }

    @Test
    void callPrivateStaticMethod() {
        TriplicateString triplicateString = cast(ObjectWithPrivateMethod.class).toInterface(TriplicateString.class);
        String one = triplicateString.triplicateString("one");
        Assertions.assertEquals("oneoneone", one);
    }


    @Test
    void passNotInterfaceForCast() {
        Assertions.assertThrows(TestprivateException.class, () -> {
            cast(ObjectWithPrivateMethod.class).toInterface(Pole.class);
        });
    }
}
