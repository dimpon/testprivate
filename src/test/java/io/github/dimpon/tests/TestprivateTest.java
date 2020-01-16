package io.github.dimpon.tests;

import static io.github.dimpon.testprivate.API.cast;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.github.dimpon.ClassB;
import io.github.dimpon.testprivate.TestprivateException;

public class TestprivateTest {

    @Test
    void callPrivateMethod() {
        ObjectWithPrivateMethod o = new ObjectWithPrivateMethod();
        DuplicateString duplicateString = cast(o).toInterface(DuplicateString.class);
        String one = duplicateString.duplicateString("one");
        Assertions.assertEquals("oneone", one);
    }

    @Test
    void setPrivateField() {
        ObjectWithPrivateMethod o = new ObjectWithPrivateMethod();
        SetString obj = cast(o).toInterface(SetString.class);
        obj.setValue("newValue");
        Assertions.assertEquals("newValue", o.readValue());
    }

    @Test
    void getPrivateField() {
        ObjectWithPrivateMethod o = new ObjectWithPrivateMethod("wiredValue");
        GetString obj = cast(o).toInterface(GetString.class);
        Assertions.assertEquals("wiredValue", obj.getValue());
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

    @Test
    void testMethodNotFound() {
        TestprivateException ex = Assertions.assertThrows(TestprivateException.class, () -> {
            cast(new OblectWithdDplicateStringMethod()).toInterface(ToLowerCase.class).nameInLowerCase();
        });

        Assertions.assertEquals("Method public abstract java.lang.String io.github.dimpon.tests.TestprivateTest$ToLowerCase.nameInLowerCase() is not found in original class/object",ex.getMessage());
    }

    interface Say {
        void say();
    }

    @Test
    void testMethodFromSuperclass() {
        ClassB b = new ClassB();
        Say say = cast(b).considerSuperclass().toInterface(Say.class);
        say.say();
    }

    @Test
    void testMethodReturnsNull() {
        OblectWithdDplicateStringMethod b = new OblectWithdDplicateStringMethod();
        DuplicateString d = cast(b).considerSuperclass().toInterface(DuplicateString.class);
        Assertions.assertNull(d.duplicateString("smth"));
    }

    @Test
    void testGetterReturnsNull() {
        OblectWithdDplicateStringMethod b = new OblectWithdDplicateStringMethod();
        GetString d = cast(b).considerSuperclass().toInterface(GetString.class);
        Assertions.assertNull(d.getValue());
    }


    public enum EnumWithPrivateMethods {
        ONE, TWO, THREE;

        private String nameInLowerCase() {
            return name().toLowerCase();
        }
    }

    enum Pole {
        Nord, South
    }

    interface SetString {
        void setValue(String newValue);
    }

    interface GetString {
        String getValue();
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

    static public class ObjectWithPrivateMethod {

        public ObjectWithPrivateMethod() {
        }

        public ObjectWithPrivateMethod(String value) {
            this.value = value;
        }

        private String value;

        private String duplicateString(String in) {
            return in + in;
        }

        private static String triplicateString(String in) {
            return in + in + in;
        }

        public String readValue() {
            return value;
        }
    }

    static public class OblectWithdDplicateStringMethod {
        private String value = null;
        private String duplicateString(String in) {
            return null;
        }
    }
}
