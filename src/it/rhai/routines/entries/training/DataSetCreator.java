package it.rhai.routines.entries.training;

import it.rhai.util.DirectoryOnlyFilter;

import java.io.File;

public class DataSetCreator {

	protected static void loadDirectory(File dir) throws Exception {
		for (File applianceSrcDir : dir.listFiles(new DirectoryOnlyFilter())) {
			for (File dataFile : applianceSrcDir.listFiles()) {
				ApplianceAdder adder = new ApplianceAdder();
				adder.addAppliance(
						dataFile.getName().substring(0,
								dataFile.getName().lastIndexOf(".")),
						applianceSrcDir.getName(), dataFile, null);
			}
		}
	}
}
