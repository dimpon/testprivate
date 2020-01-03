package io.github.dimpon.testprivate.actions;

import java.util.function.Function;

/**
 * interface Function for sneaking exceptions
 */
@FunctionalInterface
public interface FunctionWithThrows<T,R> extends Function<T,R> {

    default R apply(T t){
        try {
            return applyWithThrowable(t);
        } catch (Throwable ex) {
            return sneakyThrow(ex);
        }
    }

    R applyWithThrowable(T t) throws Throwable;

    @SuppressWarnings("unchecked")
     static <E extends Throwable, R> R sneakyThrow(Throwable t) throws E {
        throw (E) t;
    }
}
