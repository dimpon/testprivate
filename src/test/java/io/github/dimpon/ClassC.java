package io.github.dimpon;

public class ClassC {
    private int a;
    private String b;
    private Object c;
    private Long d;

    private ClassC(String result){
        this.result = result;
    }

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

    public String result;

    private ClassC(boolean a, boolean b, Boolean c, Object d) {
        this.result = "" + a + b + c + d;
    }

    private ClassC(byte a, byte b, Byte c, Object d) {
        this.result = "" + a + b + c + d;
    }

    private ClassC(short a, short b, Short c, Object d) {
        this.result = "" + a + b + c + d;
    }

    private ClassC(int a, int b, Integer c, Object d) {
        this.result = "" + a + b + c + d;
    }

    private ClassC(long a, long b, Long c, Object d) {
        this.result = "" + a + b + c + d;
    }

    private ClassC(float a, float b, Float c, Object d) {
        this.result = "" + a + b + c + d;
    }
}
