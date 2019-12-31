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

/**
 * Class allows to call methods of object using arbitrary interface containing the same methods.
 * Uses Dynamic Proxy for searching the right methods.
 */
public final class CastObject implements CastToInterface {

    private Object o;

    CastObject(Object o) {
        this.o = o;
    }

    @Override
    public InvocationHandler createInvocationHandler() {
        return new CastObject.MethodsHandler();
    }

    class MethodsHandler<T> implements InvocationHandler {
        @Override
        public Object invoke(Object __, Method method, Object[] args) throws Throwable {
            return PerformAction.perform(o, o.getClass(), method, args);
        }
    }
}