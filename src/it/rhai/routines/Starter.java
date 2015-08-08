package it.rhai.routines;

import it.rhai.settings.RHAIPropertiesSettings;
import it.rhai.settings.SettingsKeeper;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

public class Starter {

	@SuppressWarnings("rawtypes")
	public static ArrayList<Class> executables = new ArrayList<Class>();

	static {
		SettingsKeeper
				.setSettings(new RHAIPropertiesSettings(
						new File(
								"/home/simone/Documenti/Software Development/Java/Running Household Appliances Identifier/data/settings/settings.properties")));
		addExecutables();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws Exception {
		for (Class aClass : executables) {
			Executable annotation = (Executable) aClass
					.getAnnotation(Executable.class);
			if (annotation.id().equals(args[0])) {
				aClass.getMethod("execute", String[].class).invoke(null,
						new Object[] { cutArgs(args) });
				return;
			}
		}
	}

	private static void addExecutables() {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(new File(SettingsKeeper
					.getSettings().getRHAIroot()
					+ "/data/settings/executables.properties")));
			doAdd(properties);
		} catch (Exception e) {
			System.out
					.println("Unable to access config file, or config file corrupted");
		}
	}

	private static void doAdd(Properties properties)
			throws ClassNotFoundException {
		Enumeration<Object> en = properties.keys();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			executables.add(Class.forName(key));
		}
	}

	private static String[] cutArgs(String[] args) {
		String[] cutArgs = new String[args.length - 1];
		for (int i = 1; i < args.length; i++) {
			cutArgs[i-1] = args[i];
		}
		return cutArgs;
	}
}
