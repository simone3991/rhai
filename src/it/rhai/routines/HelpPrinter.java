package it.rhai.routines;

import it.rhai.util.DataHandler;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

public class HelpPrinter {

	public static <T> void print(String string, Class<T> aClass,
			DataHandler<String> printer) {
		printer.handle(string);
		printer.handle("Theese are the available routines:");
		for (Method method : aClass.getMethods()) {
			EntryPoint annotation = method.getAnnotation(EntryPoint.class);
			if (annotation != null) {
				printer.handle("----------");
				printer.handle("option: " + toString(annotation.id()));
				printer.handle("description: " + annotation.description());
				printer.handle("required args: " + toString(annotation.args()));
			}
		}
		printer.handle("----------");
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
