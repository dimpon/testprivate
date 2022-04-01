package io.github.dimpon.subclass;

import java.util.concurrent.atomic.AtomicInteger;

public class ObjectWithPrivates {
  private AtomicInteger count = new AtomicInteger(0);
  private String name;

  private String methodToTest(String in) {
    return name + in + count.incrementAndGet();
  }
}
