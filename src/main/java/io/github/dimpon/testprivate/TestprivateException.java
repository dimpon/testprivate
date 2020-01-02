package io.github.dimpon.testprivate;

public final class TestprivateException extends RuntimeException {
    public TestprivateException(String message) {
        super(message);
    }

    public TestprivateException(String message, Throwable cause) {
        super(message, cause);
    }
}
