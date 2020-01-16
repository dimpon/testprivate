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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.github.dimpon.testprivate.Action;
import io.github.dimpon.testprivate.MethodResult;

public final class SetterAction implements Action {

    @Override
    public Optional<MethodResult> performAndReturnResult(@Nonnull Object obj, @Nonnull Class<?> clazz, @Nonnull Method method, @Nullable Object[] args) {

        Predicate<Method> hasOneArgument = m -> m.getParameterTypes().length == 1;
        Predicate<Method> hasStartsWithSet = m -> m.getName().startsWith("set");
        Predicate<Method> hasReturnTypeVoid = m -> m.getReturnType().equals(void.class);

        Optional<Method> isMethodCorrect = Optional.of(method)
                .filter(hasOneArgument)
                .filter(hasStartsWithSet)
                .filter(hasReturnTypeVoid);

        if (!isMethodCorrect.isPresent())
            return Optional.empty();

        final String fieldName = fieldName(method.getName());
        Predicate<Field> hasCorrespondingName = f -> f.getName().equals(fieldName);
        Predicate<Field> hasCorrespondingType = f -> f.getType().isAssignableFrom(method.getParameterTypes()[0]);

        Optional<Field> hasField = Arrays.stream(clazz.getDeclaredFields())
                .filter(hasCorrespondingName)
                .filter(hasCorrespondingType)
                .findFirst();

        return hasField.map(f -> setFieldValue(obj, f, args));
    }

    private static String fieldName(String longName) {
        char[] chars = longName.substring(3).toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

    private static MethodResult setFieldValue(Object obj, Field field, Object[] args) {
        field.setAccessible(true);
        try {
            field.set(obj, args[0]);
            return MethodResult.successful(null);
        } catch (Throwable e) {
            return MethodResult.failed();
        }
    }
}
