package io.github.dimpon.testprivate;

public class WithClass {

	private Class<?> clazz;

	private WithClass(Class<?> clazz) {
		this.clazz = clazz;
	}

	public static WithClass Class(Class<?> clazz) {
		return new WithClass(clazz);
	}
}
