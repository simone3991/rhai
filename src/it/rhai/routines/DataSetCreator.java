package it.rhai.routines;

import it.rhai.settings.RHAIPropertiesSettings;
import it.rhai.settings.SettingsKeeper;
import it.rhai.simulation.ApplianceAdder;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class DataSetCreator {

	public static void main(String[] args) throws IOException {
		SettingsKeeper.setSettings(new RHAIPropertiesSettings(new File(
				"data/settings/settings.properties")));
		File dir = new File("/home/simone/Scrivania/RHAI_db/training");
		for (File applianceSrcDir : dir.listFiles(new FileFilter() {

			@Override
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.io.FileFilter#accept(java.io.File)
			 */
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		})) {
			for (File dataFile : applianceSrcDir.listFiles()) {
				ApplianceAdder adder = new ApplianceAdder();
				adder.addAppliance(dataFile.getName(),
						applianceSrcDir.getName(), dataFile, null);
			}
		}
	}

}
