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
import io.github.dimpon.testprivate.ConsiderSuperclass;
import io.github.dimpon.testprivate.MethodResult;
import io.github.dimpon.testprivate.TestprivateException;

/**
 * Action searches field by method name and returns its value
 */
public final class GetterAction extends ConsiderSuperclass<GetterAction> implements Action {

  @Override
  public Optional<MethodResult> performAndReturnResult(@Nonnull Object obj, @Nonnull Class<?> clazz,
      @Nonnull Method method, @Nullable Object[] args)
  {

    Predicate<Method> hasNoArguments = m -> m.getParameterTypes().length == 0;
    Predicate<Method> isStartsWithGet = m -> m.getName().startsWith("get");
    Predicate<Method> isStartsWithIs = m -> m.getName().startsWith("is");
    Predicate<Method> hasReturnTypeBoolean = m -> m.getReturnType().equals(boolean.class) || m.getReturnType()
        .equals(Boolean.class);

    Optional<Method> isMethodCorrect = Optional.of(method)
        .filter(hasNoArguments)
        .filter(isStartsWithIs.and(hasReturnTypeBoolean).or(isStartsWithGet));

      if (!isMethodCorrect.isPresent()) {return Optional.empty();}

    final String fieldName = fieldName(method.getName());

    Predicate<Field> hasCorrespondingName = f -> f.getName().equals(fieldName);
    Predicate<Field> hasCorrespondingType = f -> method.getReturnType().isAssignableFrom(f.getType());

    Optional<Field> hasField = Arrays.stream(clazz.getDeclaredFields())
        .filter(hasCorrespondingName)
        .filter(hasCorrespondingType)
        .findFirst();

    if (needToCheckSuperclass(hasField, clazz)) {
      return performAndReturnResult(obj, clazz.getSuperclass(), method, args);
    }

    return hasField.map(f -> getFieldValue(obj, f));
  }

  private static String fieldName(String longName) {
    final char[] chars;
    if (longName.startsWith("is")) {
      chars = longName.substring(2).toCharArray();
    } else if (longName.startsWith("get")) {
      chars = longName.substring(3).toCharArray();
    } else {
      throw new TestprivateException("Getter method name must start from is/get");
    }
    chars[0] = Character.toLowerCase(chars[0]);
    return new String(chars);
  }

  private static MethodResult getFieldValue(Object obj, Field field) {
    field.setAccessible(true);
    try {
      return MethodResult.successful(field.get(obj));
    } catch (Exception e) {
      return MethodResult.failed();
    }
  }
}
