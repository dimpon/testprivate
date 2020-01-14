/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2020 The Project Testprivate Authors.
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
package io.github.dimpon.testprivate.actions;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.github.dimpon.testprivate.Action;
import io.github.dimpon.testprivate.ConsiderSuperclass;

public final class MethodAction extends ConsiderSuperclass<MethodAction> implements Action {

    @Override
    public Optional<Object> performAndReturnResult(@Nonnull Object obj, @Nonnull Class<?> clazz, @Nonnull Method method, @Nullable Object[] args) {
        Predicate<Method> hasSameName = m -> m.getName().equals(method.getName());
        Predicate<Method> hasSameReturnType = m -> m.getReturnType().equals(method.getReturnType());
        Predicate<Method> hasSameArguments = m -> Arrays.deepEquals(m.getParameterTypes(), method.getParameterTypes());

        Optional<Method> hasMethod = Arrays.stream(clazz.getDeclaredMethods())
                .filter(hasSameName)
                .filter(hasSameReturnType)
                .filter(hasSameArguments)
                .findFirst();

        if (isConsiderSuperclass && !hasMethod.isPresent() && clazz.getSuperclass() != null) {
            return performAndReturnResult(obj, clazz.getSuperclass(), method, args);
        }

        return hasMethod.map((FunctionWithThrows<Method, Object>) m -> invokeMethod(obj, m, args));
    }

    private static Object invokeMethod(Object obj, Method method, Object[] args) throws Throwable {
        method.setAccessible(true);
        Object r =  method.invoke(obj, args);
        if(method.getReturnType().equals(void.class))
            return new Object();

        return r;
    }
}
