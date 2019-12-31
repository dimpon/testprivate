package io.github.dimpon.testprivate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

final class PerformAction {
    private PerformAction() {
    }

    static Object perform(Object obj, Class<?> clazz, Method method, Object[] args) throws Throwable {

        Optional<Method> exactMethod = findExactMethod(clazz, method);

        if (exactMethod.isPresent()) {
            Method declaredMethod = exactMethod.get();
            return invokeMethod(obj, declaredMethod, args);
        }

        throw new TestprivateException("Method is not found in original class/object");

    }


    private static Optional<Field> getFieldForGet(Class<?> clazz, Method method) throws Throwable {

        if (method.getName().startsWith("is") &&
                (method.getReturnType().equals(Boolean.class) || method.getReturnType().equals(boolean.class))) {


            return Optional.of(clazz.getDeclaredField(fieldName("is", method.getName())));

        }

        return Optional.empty();
    }


    private static String fieldName(String prefix, String longName) {
        char[] chars = longName.substring(prefix.length()).toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

    private static Object invokeMethod(Object obj, Method method, Object[] args) throws Throwable {
        method.setAccessible(true);
        return method.invoke(obj, args);
    }


    private static Optional<Method> findExactMethod(Class<?> clazz, Method method) {

        Predicate<Method> isSameName = m -> m.getName().equals(method.getName());
        Predicate<Method> isSameReturnType = m -> m.getReturnType().equals(method.getReturnType());
        Predicate<Method> isSameArguments = m -> equalParamTypes(m.getParameterTypes(), method.getParameterTypes());

        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(isSameName.and(isSameReturnType).and(isSameArguments))
                .findFirst();
    }

    private static boolean equalParamTypes(Class<?>[] params1, Class<?>[] params2) {
        if (params1.length == params2.length) {
            for (int i = 0; i < params1.length; i++) {
                if (params1[i] != params2[i])
                    return false;
            }
            return true;
        }
        return false;
    }


}
