package io.github.dimpon.testprivate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.github.dimpon.testprivate.WithInstance.Instance;

public class TestprivateTest {


    interface DuplicateString {
        String duplicateString(String in);
    }

    @Test
    void callPrivateMethods() {
        ObjectWithPrivateMethod o = new ObjectWithPrivateMethod();
        DuplicateString duplicateString = Instance(o).castTo(DuplicateString.class);
        String one = duplicateString.duplicateString("one");
        Assertions.assertEquals("oneone",one);
    }
}
