package io.github.dimpon.testprivate.actions;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.github.dimpon.testprivate.Action;
import io.github.dimpon.testprivate.TestprivateException;

public final class SetterAction implements Action {

    @Override
    public Optional<Object> performAndReturnResult(@Nonnull Object obj, @Nonnull Class<?> clazz, @Nonnull Method method, @Nullable Object[] args) {

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

        return hasField.map((FunctionWithThrows<Field, Object>) f -> setFieldValue(obj, f, args));
    }

    private static String fieldName(String longName) {
        char[] chars = longName.substring(3).toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

    private static Object setFieldValue(Object obj, Field field, Object[] args) throws Throwable {
        field.setAccessible(true);
        field.set(obj, args[0]);
        return new Object();
    }
}
