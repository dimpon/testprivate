package io.github.dimpon.testprivate;
import java.lang.reflect.InvocationHandler;

public abstract class CastObjectToInterface extends CastToInterface {
	protected boolean considerSuperclass = false;

	public CastToInterface considerSuperclass() {
		this.considerSuperclass = true;
		return this;
	}
}
