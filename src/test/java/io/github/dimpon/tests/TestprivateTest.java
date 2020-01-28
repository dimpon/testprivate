package io.github.dimpon.tests;

import static io.github.dimpon.testprivate.API.lookupPrivatesIn;

import io.github.dimpon.testprivate.API;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.dimpon.ClassB;
import io.github.dimpon.testprivate.TestprivateException;

public class TestprivateTest {

    @Test
    void callPrivateMethod() {
        ObjectWithPrivateMethod o = new ObjectWithPrivateMethod();
        DuplicateString duplicateString = API.lookupPrivatesIn(o).usingInterface(DuplicateString.class);
        String one = duplicateString.duplicateString("one");
        Assertions.assertEquals("oneone", one);
    }

    @Test
    void setPrivateField() {
        ObjectWithPrivateMethod o = new ObjectWithPrivateMethod();
        SetString obj = API.lookupPrivatesIn(o).usingInterface(SetString.class);
        obj.setValue("newValue");
        Assertions.assertEquals("newValue", o.readValue());
    }

    @Test
    void getPrivateField() {
        ObjectWithPrivateMethod o = new ObjectWithPrivateMethod("wiredValue");
        GetString obj = API.lookupPrivatesIn(o).usingInterface(GetString.class);
        Assertions.assertEquals("wiredValue", obj.getValue());
    }

    @Test
    void callPrivateEnumMethod() {
        EnumWithPrivateMethods e = EnumWithPrivateMethods.ONE;
        ToLowerCase toLowerCase = API.lookupPrivatesIn(e).usingInterface(ToLowerCase.class);
        String one = toLowerCase.nameInLowerCase();
        Assertions.assertEquals("one", one);
    }

    @Test
    void callPrivateStaticMethod() {
        TriplicateString triplicateString = lookupPrivatesIn(ObjectWithPrivateMethod.class).usingInterface(TriplicateString.class);
        String one = triplicateString.triplicateString("one");
        Assertions.assertEquals("oneoneone", one);
    }

    @Test
    void passNotInterfaceForCast() {
        Assertions.assertThrows(TestprivateException.class, () -> {
            lookupPrivatesIn(ObjectWithPrivateMethod.class).usingInterface(Pole.class);
        });
    }

    @Test
    void testMethodNotFound() {
        TestprivateException ex = Assertions.assertThrows(TestprivateException.class, () -> {
            API.lookupPrivatesIn(new OblectWithdDplicateStringMethod()).usingInterface(ToLowerCase.class).nameInLowerCase();
        });

        Assertions.assertEquals("Method (or field matches to method) public abstract java.lang.String io.github.dimpon.tests.TestprivateTest$ToLowerCase.nameInLowerCase() is not found in original class/object",ex.getMessage());
    }

    interface Say {
        void say();
    }

    interface Count {
        int getCount();
        void setCount(int count);
    }

    @Test
    void testMethodFromSuperclass() {
        ClassB b = new ClassB();
        Say say = API.lookupPrivatesIn(b).lookupInSuperclass().usingInterface(Say.class);
        say.say();
    }

    @Test
    void testFieldFromSuperclass() {
        ClassB b = new ClassB();
        Count count = API.lookupPrivatesIn(b).lookupInSuperclass().usingInterface(Count.class);
        count.setCount(1000);
        Assertions.assertEquals(1000,count.getCount());
    }




    @Test
    void testMethodReturnsNull() {
        OblectWithdDplicateStringMethod b = new OblectWithdDplicateStringMethod();
        DuplicateString d = API.lookupPrivatesIn(b).lookupInSuperclass().usingInterface(DuplicateString.class);
        Assertions.assertNull(d.duplicateString("smth"));
    }

    @Test
    void testGetterReturnsNull() {
        OblectWithdDplicateStringMethod b = new OblectWithdDplicateStringMethod();
        GetString d = API.lookupPrivatesIn(b).lookupInSuperclass().usingInterface(GetString.class);
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
