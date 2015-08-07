package it.rhai.routines;

import it.rhai.simulation.ApplianceAdder;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

@Executable(id = "--reload")
public class DataSetCreator {

	public static void execute(String[] args) throws IOException {
		File dir = new File(args[0]);
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
