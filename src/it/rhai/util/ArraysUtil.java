package it.rhai.util;

public class ArraysUtil {

	public static <T> String toString(T[] array, String separator,
			String starter, String ender) {
		String toString = starter == null ? "" : starter;
		for (int i = 0; i < array.length - 1; i++) {
			toString += array[i] + separator;
		}
		return toString += array[array.length - 1]
				+ (ender == null ? "" : ender);
	}

	public static <T> void shiftLeft(T[] args, int offset) {
		for (int i = offset; i < args.length; i++) {
			args[i - offset] = args[i];
		}
	}
	
	public static <T> int firstIndexOfOnly(T element, String[] array) {
		for (int i = array.length - 1; i >= 0; i--) {
			if (!element.equals(array[i])) {
				return i + 1;
			}
		}
		return -1;
	}
}