package io.github.dimpon.testprivate;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.github.dimpon.testprivate.actions.GetterAction;
import io.github.dimpon.testprivate.actions.MethodAction;
import io.github.dimpon.testprivate.actions.SetterAction;

final class PerformAction {

	private final Object obj;
	private final Class<?> clazz;
	private final Method method;
	private final Object[] args;

	private final List<Action> detectors = new ArrayList<>();

	private PerformAction(Object obj, Class<?> clazz, Method method, Object[] args) {
		this.obj = obj;
		this.clazz = clazz;
		this.method = method;
		this.args = args;

		detectors.add(new MethodAction());
		detectors.add(new SetterAction());
		detectors.add(new GetterAction());
	}

	static PerformAction create(Object obj, Class<?> clazz, Method method, Object[] args) {
		return new PerformAction(obj, clazz, method, args);
	}

	Object perform() {
		for (Action a : detectors) {
			Optional<Object> result = a.performAndReturnResult(this.obj,this.clazz,this.method,this.args);
			if (result.isPresent())
				return result.get();
		}

		throw new TestprivateException("Method is not found in original class/object");
	}
}
