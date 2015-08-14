package it.rhai.gui.identification;

import it.rhai.gui.Application;
import it.rhai.gui.util.FinalLayer;
import it.rhai.settings.RHAIPropertiesSettings;
import it.rhai.settings.SettingsKeeper;

import java.io.File;

import javax.swing.UIManager;

import com.seaglasslookandfeel.SeaGlassLookAndFeel;

public class RealTimeIdentificationStarter {

	public static void main(String[] args) throws Exception {
		SettingsKeeper
				.setSettings(new RHAIPropertiesSettings(
						new File(
								"/home/simone/Documenti/Software Development/Java/Running Household Appliances Identifier"
										+ "data/settings/settings.properties")));
		UIManager.setLookAndFeel(new SeaGlassLookAndFeel());
		Application app = new Application(new FileSearchingFrame(),
				new RealTimeIdentifierFrame(), new FinalLayer());
		app.start();
	}
}
