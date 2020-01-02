package io.github.dimpon.testprivate;
import java.lang.reflect.Method;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@FunctionalInterface
public interface Action {
	Optional<Object> performAndReturnResult(@Nonnull Object obj, @Nonnull Class<?> clazz, @Nonnull Method method, @Nullable Object[] args);
}
