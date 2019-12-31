package io.github.dimpon.testprivate;

/**
 * Starting class containing factory methods for all transformations and casting
 */
public final class API {
    private API() {
    }

    public static CastToInterface cast(Object o) {
        return new CastObject(o);
    }

    public static CastToInterface cast(Class<?> c) {
        return new CastClass(c);
    }

    public static CastToInterface cast(Enum<?> e) {
        return new CastEnum(e);
    }
}
