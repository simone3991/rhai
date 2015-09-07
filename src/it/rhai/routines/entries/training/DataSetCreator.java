package it.rhai.routines.entries.training;

import it.rhai.routines.EntryPoint;
import it.rhai.routines.HelpPrinter;
import it.rhai.simulation.ApplianceAdder;
import it.rhai.util.DirectoryOnlyFilter;
import it.rhai.util.Loggers;

import java.io.File;

public class DataSetCreator {

	@EntryPoint(id = { "-l", "-ld", "-load" }, description = "loads fingerprints from a directory", args = { "a dataset directory" })
	public static void loadLibraryFromDir(String[] args) throws Exception {
		File dir = new File(args[0]);
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

	@EntryPoint(id = { "-h", "-help", "-man" }, description = "prints a simple help", args = { "" })
	public static void help(Object... args) {
		HelpPrinter.print("Welcome to RHAI loading system",
				DataSetCreator.class, Loggers.getLogger("stdout"));
	}

}
