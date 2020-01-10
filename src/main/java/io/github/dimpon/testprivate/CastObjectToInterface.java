package io.github.dimpon.testprivate;
import java.lang.reflect.InvocationHandler;

public abstract class CastObjectToInterface extends CastToInterface {
	private boolean considerSuperclass = false;

	public CastToInterface considerSuperclass() {
		this.considerSuperclass = true;
		return this;
	}

	protected InvocationHandler createInvocationHandler() {
		return this.createInvocationHandler(considerSuperclass);
	}

	protected abstract InvocationHandler createInvocationHandler(boolean considerSuperclass);
}
