package io.github.dimpon;

public class ClassE {
  private void throwRuntimeException() {
    throw new IllegalArgumentException("argument is illegal");
  }

  private void throwException() throws ClassEException {
    throw new ClassEException("my custom exception");
  }

  public static class ClassEException extends Exception {

    public ClassEException(String message) {
      super(message);
    }
  }
}


