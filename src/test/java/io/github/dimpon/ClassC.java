package io.github.dimpon;

public class ClassC {
    private final int a;
    private final String b;
    private final Object c;
    private final Long d;


    private ClassC(int a, String b, Object c, Long d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public int getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    public Object getC() {
        return c;
    }

    public Long getD() {
        return d;
    }
}
