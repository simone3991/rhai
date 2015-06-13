package it.rhai.settings;

public class SettingsKeeper{

	private static RHAISettings instance;

	private SettingsKeeper() {
		super();
	}

	public static RHAISettings getInstance() {
		return instance;
	}
	
	public static void setInstance(RHAISettings instance) {
		SettingsKeeper.instance = instance;
	}
}
