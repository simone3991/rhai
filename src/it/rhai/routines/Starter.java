package it.rhai.routines;

import it.rhai.settings.RHAIPropertiesSettings;
import it.rhai.settings.SettingsKeeper;

import java.io.File;
import java.util.ArrayList;

public class Starter {

	@SuppressWarnings("rawtypes")
	public static ArrayList<Class> executables = new ArrayList<Class>();

	static {
		executables.add(DataSetCreator.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws Exception {
		SettingsKeeper
				.setSettings(new RHAIPropertiesSettings(
						new File(
								"/home/simone/Documenti/Software Development/Java/Running Household Appliances Identifier/data/settings/settings.properties")));

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

	private static String[] cutArgs(String[] args) {
		String[] cutArgs = new String[args.length - 1];
		for (int i = 1; i < cutArgs.length; i++) {
			cutArgs[i] = args[i];
		}
		return cutArgs;
	}
}
