package io.github.dimpon.testprivate.actions;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.github.dimpon.testprivate.Action;
import io.github.dimpon.testprivate.TestprivateException;

public final class MethodAction implements Action {

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

        return hasMethod.map((FunctionWithThrows<Method, Object>) m -> invokeMethod(obj, m, args));
    }

    private static Object invokeMethod(Object obj, Method method, Object[] args) throws Throwable {
        method.setAccessible(true);
        return method.invoke(obj, args);
    }
}
