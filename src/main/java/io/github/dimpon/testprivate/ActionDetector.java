package io.github.dimpon.testprivate;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.Optional;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface ActionDetector<A extends AccessibleObject> {
	Optional<AccessibleObjectAndAction<A>> detectActionType(@Nonnull Object obj,@Nonnull Class<?> clazz,@Nonnull Method method,@Nonnull Object[] args) throws Throwable;
}
