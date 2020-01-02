package io.github.dimpon.testprivate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public final class CastClass implements CastToInterface {

    private Class<?> c;

    CastClass(Class<?> c) {
        this.c = c;
    }

    @Override
    public InvocationHandler createInvocationHandler() {
        return new CastClass.MethodsHandler();
    }

    class MethodsHandler implements InvocationHandler {
        @Override
        public Object invoke(Object __, Method method, Object[] args) throws Throwable {
            return PerformAction.create(c, c, method, args).perform();
        }
    }
}
