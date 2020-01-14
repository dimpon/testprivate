/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2020 The Project Testprivate Authors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.github.dimpon.testprivate;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.github.dimpon.testprivate.actions.GetterAction;
import io.github.dimpon.testprivate.actions.MethodAction;
import io.github.dimpon.testprivate.actions.SetterAction;

final class PerformAction extends ConsiderSuperclass<PerformAction> {

    private final Object obj;
    private final Class<?> clazz;
    private final Method method;
    private final Object[] args;

    private PerformAction(Object obj, Class<?> clazz, Method method, Object[] args) {
        this.obj = obj;
        this.clazz = clazz;
        this.method = method;
        this.args = args;
    }

    static PerformAction create(Object obj, Class<?> clazz, Method method, Object[] args) {
        return new PerformAction(obj, clazz, method, args);
    }

    Object perform() {
        final List<Action> detectors = new ArrayList<>();
        detectors.add(new MethodAction().considerSuperclass(isConsiderSuperclass));
        detectors.add(new SetterAction());
        detectors.add(new GetterAction());

        for (Action a : detectors) {
            Optional<Object> result = a.performAndReturnResult(this.obj, this.clazz, this.method, this.args);
            if (result.isPresent())
                return result.get();
        }

        throw new TestprivateException("Method is not found in original class/object");
    }
}
