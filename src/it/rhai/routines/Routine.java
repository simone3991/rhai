package it.rhai.routines;

public class Routine {

	@EntryPoint(id = { "-h", "-help", "-man" }, description = "prints a simple help", args = { "" })
	public static void help(Object... args) {
		HelpPrinter.print("Welcome to RHAI identification system", new Object()
				.getClass().getEnclosingClass());
	}

}
