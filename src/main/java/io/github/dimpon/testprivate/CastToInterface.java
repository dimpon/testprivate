package io.github.dimpon.testprivate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public abstract class CastToInterface {

	@SuppressWarnings("unchecked")
	public <T> T toInterface(Class<T> interfaceClass) {

		if (!interfaceClass.isInterface())
			throw new TestprivateException("Only interface class can be used for casting");

		return (T) Proxy.newProxyInstance(
				this.getClass().getClassLoader(),
				new Class<?>[]{interfaceClass},
				createInvocationHandler());
	}

	protected abstract InvocationHandler createInvocationHandler();
}
