package it.rhai.settings;

/**
 * This class is designed to provide an unique and global access to a
 * {@link RHAISettings} instance. It is strongly recommended that each class of
 * the RHAI framework use that access to get settings and other informations
 * 
 * @author simone
 *
 */
public class SettingsKeeper {

	private static RHAISettings instance;

	private SettingsKeeper() {
		super();
	}

	/**
	 * Returns the global instance of {@link RHAISettings} to be used
	 * 
	 * @return: the current settings
	 */
	public static RHAISettings getSettings() {
		return instance;
	}

	/**
	 * Sets the concrete instance of {@link RHAISettings} settings to be used
	 * 
	 * @param settings
	 *            : the new instance of {@link RHAISettings} settings to be used
	 */
	public static void setSettings(RHAISettings settings) {
		SettingsKeeper.instance = settings;
	}
}
