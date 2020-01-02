package io.github.dimpon.testprivate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Class allows to call static methods of class using arbitrary interface containing the same methods.
 * Uses Dynamic Proxy for searching the right methods.
 */
public final class CastClass extends CastToInterface {

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
        public Object invoke(Object __, Method method, Object[] args) {
            return PerformAction.create(c, c, method, args).perform();
        }
    }
}
