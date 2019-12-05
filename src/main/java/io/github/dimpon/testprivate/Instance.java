/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2019 The Project Testprivate Authors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.github.dimpon.testprivate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Class allows to call methods of object using arbitrary interface containing the same methods.
 * Uses Dynamic Proxy for searching the right methods.
 */
public final class Instance {

    private Object obj;

    private Instance(Object obj) {
        this.obj = obj;
    }

    /**
     * Factory method creates the instance of Instance
     * @param obj object is needed to be tested
     * @return Instance object
     */
    public static Instance instance(Object obj) {
        return new Instance(obj);
    }

    /**
     * Generates dynamic proxy for interfaceClass interface
     *
     * @param interfaceClass interface for proxing original object
     * @param <T> interface class
     * @return instance of  interfaceClass
     */
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
