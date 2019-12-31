package io.github.dimpon.testprivate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public final class CastEnum implements CastToInterface {

    private Enum<?> e;

    CastEnum(Enum<?> e) {
        this.e = e;
    }

    @Override
    public InvocationHandler createInvocationHandler() {
        return new CastEnum.MethodsHandler();
    }

    class MethodsHandler<T> implements InvocationHandler {
        @Override
        public Object invoke(Object __, Method method, Object[] args) throws Throwable {
            return PerformAction.perform(e, e.getClass(), method, args);
        }
    }
}
