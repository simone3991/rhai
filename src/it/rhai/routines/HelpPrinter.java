package it.rhai.routines;

import java.lang.reflect.Method;

public class HelpPrinter {

	public static <T> void print(String string, Class<T> aClass) {
		System.out.println(string);
		System.out.println("Theese are the available options:");
		for (Method method : aClass.getMethods()) {
			EntryPoint annotation = method.getAnnotation(EntryPoint.class);
			if (annotation != null) {
				System.out.println("option: " + annotation.id() + "\t\t"
						+ "description: " + annotation.description() + "\t\t"
						+ "required args: " + toString(annotation.args()));
			}
		}
	}

	private static String toString(String[] args) {
		String string = "";
		boolean first = true;
		for (String arg : args) {
			if (first) {
				string += string + arg;
				first = false;
			} else {
				string += string + " , " + arg;
			}
		}
		return string;
	}
}
