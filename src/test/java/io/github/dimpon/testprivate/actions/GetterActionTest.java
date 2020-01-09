package io.github.dimpon.testprivate.actions;

import static io.github.dimpon.testprivate.API.cast;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GetterActionTest {

    @Test
    void getPrivateString() {
        SomePOJOClass o = new SomePOJOClass("hello");
        GetString obj = cast(o).toInterface(GetString.class);
        Assertions.assertEquals("hello", obj.getVal());
    }

    @Test
    void getPrivateBoolean() {
        SomePOJOClass o = new SomePOJOClass(true);
        GetBoolean obj = cast(o).toInterface(GetBoolean.class);
        Assertions.assertTrue(obj.isFinished());
        Assertions.assertFalse(obj.isNotFinished());
    }

    @Test
    void getPrivateList() {
        ArrayList arrayList = new ArrayList();
        SomePOJOClass o = new SomePOJOClass(arrayList);
        GetList obj = cast(o).toInterface(GetList.class);
        Assertions.assertEquals(arrayList, obj.getMyList());
    }

    @Test
    void testStaticFiedAccess() {
        NameForAll obj = cast(SomePOJOClass.class).toInterface(NameForAll.class);
        obj.setNameForAll("it is static");
        Assertions.assertEquals("it is static", obj.getNameForAll());
    }

    interface GetList {
        List getMyList();
    }

    interface GetString {
        String getVal();
    }

    interface GetBoolean {
        Boolean isFinished();
        boolean isNotFinished();
    }

    interface NameForAll {
        String getNameForAll();
        void setNameForAll(String s);
    }

    static class SomePOJOClass {
        private String val;
        private ArrayList myList;
        private Boolean finished;
        private boolean notFinished;

        private static String nameForAll;

        SomePOJOClass(String val) {
            this.val = val;
        }

        SomePOJOClass(ArrayList myList) {
            this.myList = myList;
        }

        SomePOJOClass(Boolean finished) {
            this.finished = finished;
            this.notFinished = !finished;
        }
    }
}
