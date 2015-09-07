package it.rhai.routines.entries.training;

import it.rhai.routines.EntryPoint;
import it.rhai.routines.HelpPrinter;
import it.rhai.util.Loggers;

import java.io.File;

public class TrainerEntry {

	@EntryPoint(id = { "-l", "-ld", "-load" }, description = "loads fingerprints from a directory", args = { "a dataset directory" })
	public static void loadLibraryFromDir(String[] args) throws Exception {
		File dir = new File(args[0]);
		DataSetCreator.loadDirectory(dir);
	}

	@EntryPoint(args = { "a real dataset", "a fake dataset" }, description = "computes the best acceptance likelihood", id = {
			"-o", "-optimize" })
	public static void optimize(String[] args) throws Exception {
		File dataset = new File(args[0]);
		File fake = new File(args[1]);
		System.out
				.println("The acceptance likelihood that optimizes static performances is "
						+ String.format("%.2f", AcceptanceLikelihoodOptimizer
								.optimize(dataset, fake)));
		;
	}

	@EntryPoint(id = { "-h", "-help", "-man" }, description = "prints a simple help", args = { "" })
	public static void help(Object... args) {
		HelpPrinter.print("Welcome to RHAI training system",
				TrainerEntry.class, Loggers.getLogger("stdout"));
	}

}
