package io.github.dimpon.testprivate;

public abstract class LookupInSuperclass extends UsingInterface {
  protected boolean considerSuperclass = false;

  public UsingInterface lookupInSuperclass() {
    this.considerSuperclass = true;
    return this;
  }
}
