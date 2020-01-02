package io.github.dimpon.testprivate.actions;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.apache.commons.text.CaseUtils;

import io.github.dimpon.testprivate.AccessibleObjectAndAction;
import io.github.dimpon.testprivate.ActionDetector;
import io.github.dimpon.testprivate.ActionType;

public class SetterDetector implements ActionDetector<Field> {

	@Override
	public Optional<AccessibleObjectAndAction<Field>> detectActionType(@Nonnull Object obj, @Nonnull Class<?> clazz, @Nonnull Method method,
			@Nonnull Object[] args) throws Throwable {

		if (args.length != 1)
			return Optional.empty();

		if (!method.getName().startsWith("set"))
			return Optional.empty();

		if (!method.getReturnType().equals(void.class)) {
			return Optional.empty();
		}

		final String fieldName = fieldName(method.getName());
		final Class<?> fieldType = args[0].getClass();

		clazz.getDeclaredFields();

		Optional<Field> hasField = Arrays.stream(clazz.getDeclaredFields())
				.filter(field -> field.getName().equals(fieldName) && field.getType().equals(fieldType))
				.findFirst();

		return hasField.map(f -> new AccessibleObjectAndAction<>(ActionType.SET, f));
	}

	private static String fieldName(String longName) {
		String fieldName = longName.substring(3);
		return CaseUtils.toCamelCase(fieldName, false);
	}

}
