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
package io.github.dimpon.testprivate;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * class creates an instance of C using params passing to {@link InstanceCreator#withArguments}
 *
 * @param <C> type
 */
public class InstanceCreator<C> {
    private final Class<C> classToInstantiate;

    InstanceCreator(Class<C> classToInstantiate) {
        this.classToInstantiate = classToInstantiate;
    }

    public C withArguments(Object... args) {

        final Class<?>[] argsTypes = Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);

        @SuppressWarnings("unchecked")
        final Constructor<C>[] constructors = (Constructor<C>[]) this.classToInstantiate.getDeclaredConstructors();


        for (Constructor<C> constructor : constructors) {
            Class<?>[] constrArgTypes = constructor.getParameterTypes();

            if (isMatched(argsTypes, constrArgTypes)) {
                constructor.setAccessible(true);
                try {
                    return constructor.newInstance(args);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new TestprivateException("No suitable constructor", e);
                }
            }
        }

        throw new TestprivateException("No suitable constructor");
    }

    public C usingDefaultConstructor(){
        return withArguments();
    }


    private static boolean isMatched(Class<?>[] argsTypes, Class<?>[] constrArgTypes) {

        if (argsTypes.length != constrArgTypes.length)
            return false;

        boolean result = true;

        for (int i = 0; i < constrArgTypes.length; i++) {
            result &= constrArgTypes[i].isAssignableFrom(argsTypes[i]) | matchingWithPrimitives(argsTypes[i], constrArgTypes[i]);
        }

        return result;
    }

    private static boolean matchingWithPrimitives(Class<?> argType, Class<?> constrArgType) {
        if (!constrArgType.isPrimitive())
            return false;

        ///
        if (argType.equals(Byte.class) && constrArgType.equals(byte.class))
            return true;
        ///
        if (argType.equals(Short.class) && constrArgType.equals(short.class))
            return true;
        ///
        if (argType.equals(Integer.class) && constrArgType.equals(int.class))
            return true;
        ///
        if (argType.equals(Long.class) && constrArgType.equals(long.class))
            return true;
        ///
        if (argType.equals(Float.class) && constrArgType.equals(float.class))
            return true;
        ///
        if (argType.equals(Double.class) && constrArgType.equals(double.class))
            return true;
        ////
        if (argType.equals(Boolean.class) && constrArgType.equals(boolean.class))
            return true;
        ////
        if (argType.equals(Character.class) && constrArgType.equals(char.class))
            return true;

        return false;
    }

}
