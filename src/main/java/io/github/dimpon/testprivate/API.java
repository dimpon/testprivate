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

/**
 * Starting class containing factory methods for all transformations and casting.
 * For casting class/object to arbitrary interface the dynamic proxy is used.
 * Then, once a method of dynamic proxy is called, the corresponding method of class/object is searched.
 */
public final class API {
	private API() {
	}

	/**
	 * Passing class contains private methods or fields.
	 *
	 * @param obj object is intended to be casted.
	 * @return {@link UsingInterface} with single method {@link UsingInterface#usingInterface(Class)} which returns dynamic proxy of interface.
	 */
	public static LookupInSuperclass lookupPrivatesIn(Object obj) {
		return new LookupInObject(obj);
	}

	/**
	 * Passing class contains private methods or fields.
	 * Need to understand, that static methods of the class will be invoked.
	 *
	 * @param cla class is intended to be casted.
	 * @return {@link UsingInterface} with single method {@link UsingInterface#usingInterface(Class)} which returns dynamic proxy of interface.
	 */
	public static UsingInterface lookupPrivatesIn(Class<?> cla) {
		return new LookupInClass(cla);
	}


	/**
	 * Passing class of C for instantiation object.
	 *
	 * @param cla class
	 * @param <C> type
	 * @return  {@link InstanceCreator} with single method {@link InstanceCreator#withArguments(Object...)} which instantiate the class.
	 */
	public static <C> InstanceCreator<C> createInstanceOf(Class<C> cla) {
		return new InstanceCreator<C>(cla);
	}
}
