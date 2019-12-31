package io.github.dimpon.testprivate;

public enum EnumWithPrivateMethods {
    ONE,TWO,THREE;

    private String nameInLowerCase(){
        return name().toLowerCase();
    }
}
