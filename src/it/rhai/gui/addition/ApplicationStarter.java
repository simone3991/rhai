package it.rhai.gui.addition;

import java.io.File;

import it.rhai.gui.Application;
import it.rhai.settings.RHAIPropertiesSettings;
import it.rhai.settings.SettingsKeeper;

import javax.swing.UIManager;

import com.seaglasslookandfeel.SeaGlassLookAndFeel;

public class ApplicationStarter {

	public static void main(String[] args) throws Exception {
		SettingsKeeper
				.setSettings(new RHAIPropertiesSettings(
						new File(
								"/home/simone/Documenti/Software Development/Java/Running Household Appliances Identifier"
										+ "data/settings/settings.properties")));
		UIManager.setLookAndFeel(new SeaGlassLookAndFeel());
		Application app = new Application(new ApplianceAdderFrame());
		app.start();
	}

}
