package io.github.dimpon.testprivate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public final class Instance {

    private Object obj;

    private Instance(Object obj) {
        this.obj = obj;
    }

    public static Instance Instance(Object obj) {
        return new Instance(obj);
    }

    @SuppressWarnings("unchecked")
    public <T> T castTo(Class<T> interfaceClass) {

        return (T) Proxy.newProxyInstance(
                Instance.class.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new io.github.dimpon.testprivate.Instance.MethodsHandler(obj));

    }

    class MethodsHandler<T> implements InvocationHandler {

        private T obj;

        private MethodsHandler(T obj) {
            this.obj = obj;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method declaredMethod = obj.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
            declaredMethod.setAccessible(true);
            return declaredMethod.invoke(obj, args);
        }
    }
}
