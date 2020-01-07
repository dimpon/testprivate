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
	 * Passing object which is intended to be casted.
	 *
	 * @param obj object is intended to be casted.
	 * @return {@link CastToInterface} with single method {@link CastToInterface#toInterface(Class)} which returns dynamic proxy of interface.
	 */
	public static CastToInterface cast(Object obj) {
		return new CastObject(obj);
	}

	/**
	 * Passing class which is intended to be casted.
	 * Need to understand, that static methods of the class will be invoked.
	 *
	 * @param cla class is intended to be casted.
	 * @return {@link CastToInterface} with single method {@link CastToInterface#toInterface(Class)} which returns dynamic proxy of interface.
	 */
	public static CastToInterface cast(Class<?> cla) {
		return new CastClass(cla);
	}

	/**
	 * Passing enum which is intended to be casted.
	 *
	 * @param enu enum is intended to be casted.
	 * @return {@link CastToInterface} with single method {@link CastToInterface#toInterface(Class)} which returns dynamic proxy of interface.
	 */
	public static CastToInterface cast(Enum<?> enu) {
		return new CastEnum(enu);
	}
}
