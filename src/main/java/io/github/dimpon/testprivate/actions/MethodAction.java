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
		Predicate<Method> hasSameArguments = m -> equalParamTypes(m.getParameterTypes(), method.getParameterTypes());

		Optional<Method> hasMethod = Arrays.stream(clazz.getDeclaredMethods())
				.filter(hasSameName)
				.filter(hasSameReturnType)
				.filter(hasSameArguments)
				.findFirst();

		return hasMethod.flatMap(m -> Optional.of(invokeMethod(obj, m, args)));
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

	private static Object invokeMethod(Object obj, Method method, Object[] args) {
		try {
			method.setAccessible(true);
			return method.invoke(obj, args);
		} catch (Throwable e) {
			throw new TestprivateException(e.getMessage(), e);
		}
	}
}
