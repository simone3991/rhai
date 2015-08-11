package it.rhai.routines;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

public class HelpPrinter {

	public static <T> void print(String string, Class<T> aClass) {
		System.out.println(string);
		System.out.println("Theese are the available options:");
		for (Method method : aClass.getMethods()) {
			EntryPoint annotation = method.getAnnotation(EntryPoint.class);
			if (annotation != null) {
				System.out.println("----------");
				System.out.println("option: " + toString(annotation.id()));
				System.out.println("description: " + annotation.description());
				System.out.println("required args: "
						+ toString(annotation.args()));
			}
		}
		System.out.println("----------");
	}

	private static String toString(String[] args) {
		Arrays.sort(args, new Comparator<String>() {

			@Override
			public int compare(String string1, String string2) {
				return Integer.valueOf(string1.length()).compareTo(
						Integer.valueOf(string2.length()));
			}
		});
		String string = "";
		boolean first = true;
		for (String arg : args) {
			if (first) {
				string += arg;
				first = false;
			} else {
				string += " , " + arg;
			}
		}
		return string;
	}
}
