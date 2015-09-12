package it.rhai.routines.entries.training;

import it.rhai.settings.SettingsKeeper;
import it.rhai.util.DirectoryOnlyFilter;

import java.io.File;

public class DataSetCreator {

	public static void loadDirectory(File dir) throws Exception {
		for (File applianceSrcDir : dir.listFiles(new DirectoryOnlyFilter())) {
			SettingsKeeper.getSettings().getOutput()
					.handle("Loading " + applianceSrcDir.getName() + " ...");
			for (File dataFile : applianceSrcDir.listFiles()) {
				ApplianceAdder adder = new ApplianceAdder();
				adder.addAppliance(
						dataFile.getName().substring(0,
								dataFile.getName().lastIndexOf(".")),
						applianceSrcDir.getName(), dataFile, null);
			}
		}
		SettingsKeeper.getSettings().getOutput()
				.handle("Your database is now available");
	}
}
