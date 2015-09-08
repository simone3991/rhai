package it.rhai.util;

public class ArraysUtil {

	public static String toString(Object[] array, String separator,
			String starter, String ender) {
		String toString = starter == null ? "" : starter;
		for (int i = 0; i < array.length - 1; i++) {
			toString += array[i] + separator;
		}
		return toString += array[array.length - 1] + ender == null ? "" : ender;
	}

	public static void shiftLeft(String[] args, int offset) {
		for (int i = offset; i < args.length; i++) {
			args[i - offset] = args[i];
		}
	}
}