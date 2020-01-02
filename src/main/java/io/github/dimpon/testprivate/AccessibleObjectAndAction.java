package io.github.dimpon.testprivate;
import java.lang.reflect.AccessibleObject;

public class AccessibleObjectAndAction<A extends AccessibleObject> {
	final ActionType actionType;
	final A accessibleObject;

	public AccessibleObjectAndAction(ActionType actionType, A accessibleObject) {
		this.actionType = actionType;
		this.accessibleObject = accessibleObject;
	}
}

