package it.rhai.routines;

import it.rhai.routines.entries.options.Options;
import it.rhai.settings.RHAIPropertiesSettings;
import it.rhai.settings.SettingsKeeper;
import it.rhai.util.ArraysUtil;
import it.rhai.util.DataHandler;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

public class Starter {

	public static Properties commands = new Properties();
	public static Properties commandsDescrip = new Properties();

	static {
		SettingsKeeper
				.setSettings(new RHAIPropertiesSettings(
						new File(
								"/home/simone/Documenti/Software Development/Java/Running Household Appliances Identifier/data/settings/settings.properties")));
		addExecutables();
	}

	public static void main(String[] args) {
		try {
			if (Options.containsOption(args[0])) {
				String option = args[0];
				ArraysUtil.shiftLeft(args, 1);
				Options.saveCommands(commands, commandsDescrip);
				Options.exec(option, args);
			}
			execv(args);

		} catch (Exception exception) {
			System.out.println("Error occured: " + exception.toString());
			DataHandler<Throwable> printer = new DataHandler<Throwable>() {

				private DataHandler<String> handler = SettingsKeeper
						.getSettings().getDebugLogger();

				@Override
				public void handle(Throwable toBeHandled) {
					printStackTrace(toBeHandled);
					handler.handle("Caused by: "
							+ toBeHandled.getCause().toString());
					printStackTrace(toBeHandled.getCause());
				}

				private void printStackTrace(Throwable toBeHandled) {
					for (StackTraceElement stackTraceElement : toBeHandled
							.getStackTrace()) {
						handler.handle(stackTraceElement.toString());
					}
				}
			};
			printer.handle(exception);
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
}
