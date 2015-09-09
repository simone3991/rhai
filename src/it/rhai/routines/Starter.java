package it.rhai.routines;

import it.rhai.settings.RHAIPropertiesSettings;
import it.rhai.settings.SettingsKeeper;
import it.rhai.util.ArraysUtil;
import it.rhai.util.DataHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

public class Starter {

	public static Properties commands = new Properties();
	public static Properties commandsDescrip = new Properties();
	private static HashMap<String, DataHandler<String[]>> options = new HashMap<String, DataHandler<String[]>>();

	static {
		SettingsKeeper
				.setSettings(new RHAIPropertiesSettings(
						new File(
								"/home/simone/Documenti/Software Development/Java/Running Household Appliances Identifier/data/settings/settings.properties")));
		addExecutables();
		addOptions();
	}

	public static void main(String[] args) {
		try {
			if (options.containsKey(args[0])) {
				String option = args[0];
				ArraysUtil.shiftLeft(args, 1);
				options.get(option).handle(args);
			}
			execv(args);

		} catch (Exception exception) {
			System.out.println("Error occured: " + exception.toString());
			(new DataHandler<StackTraceElement[]>() {

				@Override
				public void handle(StackTraceElement[] toBeHandled) {
					for (StackTraceElement stackTraceElement : toBeHandled) {
						SettingsKeeper.getSettings().getDebugLogger()
								.handle(stackTraceElement.toString());
					}
				}
			}).handle(exception.getStackTrace());
			System.out
					.println("Try '--help' for further informations about usage");
		}
	}

	private static void execv(String[] args) throws ClassNotFoundException,
			IllegalAccessException, InvocationTargetException {
		Class<?> aClass = Class.forName(commands.getProperty(args[0]));
		for (Method method : aClass.getMethods()) {
			if (method.isAnnotationPresent(EntryPoint.class)) {
				if (Arrays.asList(
						((EntryPoint) method.getAnnotation(EntryPoint.class))
								.id()).contains(args[1])) {
					SettingsKeeper
							.getSettings()
							.getOutput()
							.handle("\n[ command "
									+ args[0]
									+ " with option "
									+ args[1]
									+ " started"
									+ ", "
									+ (new SimpleDateFormat(
											"E dd MM yyyy HH:mm"))
											.format(new Date()) + " ]");
					execOption(args, method);
					return;
				}
			}
		}
	}

	private static void execOption(String[] args, Method method)
			throws IllegalAccessException, InvocationTargetException {
		long start = System.nanoTime();
		ArraysUtil.shiftLeft(args, 2);
		method.invoke(null, new Object[] { args });
		SettingsKeeper
				.getSettings()
				.getOutput()
				.handle("[ process terminated in about " + getTime(start)
						+ " ]");
		SettingsKeeper.getSettings().getOutput().handle("");
	}

	private static String getTime(long start) {
		return String.format("%.2f", (System.nanoTime() - start) / 1000000000f)
				+ " seconds";
	}

	private static void addExecutables() {
		try {
			commands.load(new FileInputStream(new File(SettingsKeeper
					.getSettings().getRHAIroot()
					+ "/data/settings/executables.properties")));
			commandsDescrip.load(new FileInputStream(new File(SettingsKeeper
					.getSettings().getRHAIroot()
					+ "/data/settings/executables.description")));
		} catch (Exception e) {
			System.out
					.println("Unable to access config file, or config file corrupted");
		}
	}

	private static void addOptions() {
		options.put("--save", new DataHandler<String[]>() {

			@Override
			public void handle(final String[] toBeHandled) {
				final String fileName = toBeHandled[0];
				SettingsKeeper.getSettings().setOutput(
						new DataHandler<String>() {

							@Override
							public void handle(String toBePrinted) {
								try {
									File file = new File(fileName);
									BufferedWriter writer = new BufferedWriter(
											new FileWriter(file, true));
									writer.write(toBePrinted);
									writer.newLine();
									writer.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						});
				ArraysUtil.shiftLeft(toBeHandled, 1);
			}
		});

		options.put("--help", new DataHandler<String[]>() {

			@Override
			public void handle(String[] toBeHandled) {
				System.out
						.println("\nWelcome to the Running Household Appliances Identifier");
				System.out.printf("\n%10s", "Commands:\n");
				Enumeration<Object> keys = commands.keys();
				while (keys.hasMoreElements()) {
					String key = (String) keys.nextElement();
					System.out.printf("%10s %-20s %s\n", "", key,
							commandsDescrip.getProperty(key));
				}
				System.out.printf("\n%10s", "Options:\n");
				Iterator<String> opts = options.keySet().iterator();
				while (opts.hasNext()) {
					String key = (String) opts.next();
					System.out.printf("%10s %-20s %s\n", "", key,
							"description here");
				}
				System.out.printf("\n%10s", "Usage:\n");
				System.out
						.printf("%10s %s\n\n", "",
								"<option> <option-args> <command> <routine-id> <routine-args>");
				System.exit(0);
			}
		});
	}
}
