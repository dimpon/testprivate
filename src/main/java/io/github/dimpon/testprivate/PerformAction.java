package io.github.dimpon.testprivate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

import io.github.dimpon.testprivate.actions.MethodDetector;
import io.github.dimpon.testprivate.actions.SetterDetector;

final class PerformAction {

	private final Object obj;
	private final Class<?> clazz;
	private final Method method;
	private final Object[] args;
	private ActionDetector<Method> methodDetector = new MethodDetector();
	private ActionDetector<Field> setterDetector = new SetterDetector();

	private PerformAction(Object obj, Class<?> clazz, Method method, Object[] args) {
		this.obj = obj;
		this.clazz = clazz;
		this.method = method;
		this.args = args;
	}

	static PerformAction create(Object obj, Class<?> clazz, Method method, Object[] args) {
		return new PerformAction(obj, clazz, method, args);
	}

	Object perform() throws Throwable {

		Optional<AccessibleObjectAndAction<Method>> methodAndAction = methodDetector
				.detectActionType(this.obj, this.clazz, this.method, this.args);

		if (methodAndAction.isPresent()) {
			return invokeMethod(obj, methodAndAction.get().accessibleObject, args);
		}

		Optional<AccessibleObjectAndAction<Field>> fieldsAndAction = setterDetector
				.detectActionType(this.obj, this.clazz, this.method, this.args);

		if (fieldsAndAction.isPresent()) {
			setField(this.obj, fieldsAndAction.get().accessibleObject, args[0]);
			return null;
		}

		throw new TestprivateException("Method is not found in original class/object");

	}

	/*private static Optional<Field> getFieldForGet(Class<?> clazz, Method method) throws Throwable {

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
	}*/

	private Object invokeMethod(Object obj, Method method, Object[] args) throws Throwable {
		method.setAccessible(true);
		return method.invoke(obj, args);
	}

	private void setField(Object obj, Field field, Object newValue) throws Throwable {
		field.setAccessible(true);
		field.set(obj, newValue);
	}

}
