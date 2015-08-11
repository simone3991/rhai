package it.rhai.routines;

import it.rhai.settings.RHAIPropertiesSettings;
import it.rhai.settings.SettingsKeeper;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Properties;

public class Starter {

	public static Properties config = new Properties();

	static {
		SettingsKeeper
				.setSettings(new RHAIPropertiesSettings(
						new File(
								"/home/simone/Documenti/Software Development/Java/Running Household Appliances Identifier/data/settings/settings.properties")));
		addExecutables();
	}

	@SuppressWarnings({ "rawtypes" })
	public static void main(String[] args) {
		try {
			if (!args[0].equals("--help")) {
				Class aClass = Class.forName(config.getProperty(args[0]));
				for (Method method : aClass.getMethods()) {
					if (method.isAnnotationPresent(EntryPoint.class)) {
						if (((EntryPoint) method
								.getAnnotation(EntryPoint.class)).id().equals(
								args[1])) {
							method.invoke(null, new Object[] { cutArgs(args) });
							return;
						}
					}
				}
				System.out.println("No available option " + args[1]
						+ " for command " + args[0]);
			} else {
				System.out.println("RHAI: help system");
				System.out.println("Available commands:\n");
				Enumeration<Object> keys = config.keys();
				while (keys.hasMoreElements()) {
					String key = (String) keys.nextElement();
					System.out.println(key);
				}
				System.out.println();
			}
		} catch (Exception exception) {
			System.out.println("Error occured: "+exception.toString());
			exception.printStackTrace();
			System.out.println("Try --help for further informations about usage");
		}
	}

	private static void addExecutables() {
		try {
			config.load(new FileInputStream(new File(SettingsKeeper
					.getSettings().getRHAIroot()
					+ "/data/settings/executables.properties")));
		} catch (Exception e) {
			System.out
					.println("Unable to access config file, or config file corrupted");
		}
	}

	private static String[] cutArgs(String[] args) {
		String[] cutArgs = new String[args.length - 2];
		for (int i = 2; i < args.length; i++) {
			cutArgs[i - 2] = args[i];
		}
		return cutArgs;
	}
}
