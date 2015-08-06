package it.rhai.test;

import it.rhai.settings.RHAIPropertiesSettings;
import it.rhai.settings.SettingsKeeper;
import it.rhai.simulation.ApplianceAdder;

import java.io.File;
import java.io.IOException;

public class LibraryAdditionTest {

	public static void main(String[] args) throws IOException {
		SettingsKeeper.setSettings(new RHAIPropertiesSettings(new File(
				"settings/settings.dat")));
		ApplianceAdder adder = new ApplianceAdder();
		adder.addAppliance("tmp", new File("data/testing.dat"), new File(
				"data/icons/forno.png"));
	}
}
