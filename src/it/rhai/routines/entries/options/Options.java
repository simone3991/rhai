package it.rhai.routines.entries.options;

import it.rhai.routines.EntryPoint;
import it.rhai.settings.SettingsKeeper;
import it.rhai.util.ArraysUtil;
import it.rhai.util.DataHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;

public class Options {

	private static Properties myCommands;
	private static Properties myCommandsDescrip;

	@EntryPoint(args = { "an output file path" }, description = "Save the output into a file", id = { "--save" })
	public static void save(String[] args) {
		final String fileName = args[0];
		SettingsKeeper.getSettings().setOutput(new DataHandler<String>() {

			@Override
			public void handle(String toBePrinted) {
				try {
					File file = new File(fileName);
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write(toBePrinted);
					writer.newLine();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		ArraysUtil.shiftLeft(args, 1);
	}

	@EntryPoint(args = { "" }, description = "Prints RHAI usage manual", id = { "--help" })
	public static void help(String[] args) {
		System.out
				.println("\nWelcome to the Running Household Appliances Identifier");
		System.out.printf("\n%10s", "Commands:\n");
		Enumeration<Object> keys = myCommands.keys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			System.out.printf("%10s %-20s %s\n", "", key,
					myCommandsDescrip.getProperty(key));
		}
		System.out.printf("\n%10s", "Options:\n");
		for (Method method : Options.class.getMethods()) {
			if (method.isAnnotationPresent(EntryPoint.class)) {
				EntryPoint info = (EntryPoint) method
						.getAnnotation(EntryPoint.class);
				System.out.printf(
						"%10s %-20s %-50s %s\n",
						"",
						ArraysUtil.toString(info.id(), ", ", null, null),
						"requires "
								+ ArraysUtil.toString(info.args(), ", ", "<",
										">"), info.description());
			}
		}
		System.out.printf("\n%10s", "Usage:\n");
		System.out.printf("%10s %s\n\n", "",
				"<option> <option-args> <command> <routine-id> <routine-args>");
		System.exit(0);
	}

	@EntryPoint(args = { "" }, description = "Prints info about this project", id = { "--info" })
	public static void info(String[] args) {
		System.out.println();
		System.out.println("RHAI version \"1.0.1\"");
		System.out.println("Author: Simone Colucci");
		System.out
				.println("Credits: Tullio Facchinetti, Cristiana Larizza, Davide Caprino");
		System.out.println("Website: http://simone3991.github.io/rhai");
		System.out.println();
		System.exit(0);
	}

	public static boolean containsOption(String option) {
		for (Method method : Options.class.getMethods()) {
			if (method.isAnnotationPresent(EntryPoint.class)) {
				if (Arrays.asList(method.getAnnotation(EntryPoint.class).id())
						.contains(option)) {
					return true;
				}
			}
		}
		return false;
	}

	public static void saveCommands(Properties commands,
			Properties commandsDescrip) {
		myCommands = commands;
		myCommandsDescrip = commandsDescrip;
	}

	public static void exec(String option, String[] args) throws Exception {
		for (Method method : Options.class.getMethods()) {
			if (method.isAnnotationPresent(EntryPoint.class)) {
				if (Arrays.asList(method.getAnnotation(EntryPoint.class).id())
						.contains(option)) {
					method.invoke(null, new Object[] { args });
				}
			}
		}
	}
}
