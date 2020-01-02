package io.github.dimpon.testprivate;

public class ObjectWithPrivateMethod {

    private String value;

    private String duplicateString(String in){
        return in+in;
    }

    private static String triplicateString(String in){
        return in+in+in;
    }

    public String readValue() {
        return value;
    }
}
