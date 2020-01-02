package io.github.dimpon.testprivate.actions;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import javax.annotation.Nonnull;

import io.github.dimpon.testprivate.AccessibleObjectAndAction;
import io.github.dimpon.testprivate.ActionDetector;
import io.github.dimpon.testprivate.ActionType;

public class MethodDetector implements ActionDetector<Method> {
	@Override
	public Optional<AccessibleObjectAndAction<Method>> detectActionType(@Nonnull Object obj, @Nonnull Class<?> clazz, @Nonnull Method method,
			@Nonnull Object[] args) throws Throwable {
		Predicate<Method> isSameName = m -> m.getName().equals(method.getName());
		Predicate<Method> isSameReturnType = m -> m.getReturnType().equals(method.getReturnType());
		Predicate<Method> isSameArguments = m -> equalParamTypes(m.getParameterTypes(), method.getParameterTypes());

		Optional<Method> hasMethod = Arrays.stream(clazz.getDeclaredMethods())
				.filter(isSameName.and(isSameReturnType).and(isSameArguments))
				.findFirst();

		return hasMethod.map(m -> new AccessibleObjectAndAction<>(ActionType.METHOD, m));
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
