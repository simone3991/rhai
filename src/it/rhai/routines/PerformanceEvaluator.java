package it.rhai.routines;

import it.rhai.util.DataHandler;
import it.rhai.util.DirectoryOnlyFilter;

import java.io.File;

public class PerformanceEvaluator {

	private static int successes, globalSuccesses = 0;
	private static int trials, globalTrials = 0;
	private static String globalRecognized;
	private static int counter = 0;

	@EntryPoint(id = { "-s", "-static" }, description = "returns the static performance", args = { "a dataset directory" })
	public static void evaluateDir(String[] args) throws Exception {
		File dir = new File(args[0]);
		evaluateDir(dir);
		String perc = computePerc(globalSuccesses, globalTrials);
		System.out.println("The overall performance computation resulted in a "
				+ perc + " of success");
	}

	@EntryPoint(id = { "-d", "-dynamic" }, description = "returns the dynamic performance", args = { "a dataset directory" })
	public static void evaluateDirDyn(String[] args) throws Exception {
		File dir = new File(args[0]);
		evaluateDirDyn(dir);
		String perc = computePerc(globalSuccesses, globalTrials);
		System.out.println("The overall performance computation resulted in a "
				+ perc + " of success");
	}

	private static void evaluateDir(File dir) throws Exception {
		for (File applianceSrcDir : dir.listFiles(new DirectoryOnlyFilter())) {
			evaluateSingleAppliance(applianceSrcDir);
		}
	}

	private static void evaluateDirDyn(File dir) throws Exception {
		for (File applianceSrcDir : dir.listFiles(new DirectoryOnlyFilter())) {
			evaluateSingleApplianceDyn(applianceSrcDir);
		}
	}

	private static void evaluateSingleAppliance(File applianceSrcDir)
			throws Exception {
		String appliance = applianceSrcDir.getName();
		successes = trials = 0;
		for (File dataFile : applianceSrcDir.listFiles()) {
			trials++;
			globalTrials++;
			RHAI.identifyAppliance(dataFile, new DataHandler<String>() {

				@Override
				public void handle(String toBeHandled) {
					globalRecognized = toBeHandled;
				}
			});
			handleResultStatic(appliance);
		}
		String perc = computePerc(successes, trials);
		System.out.println("The identification of " + applianceSrcDir.getName()
				+ " resulted in a " + perc + " of success (" + successes + "/"
				+ trials + ")");
	}

	private static void handleResultStatic(String appliance) {
		if (appliance.equals(globalRecognized)) {
			successes++;
			globalSuccesses++;
		}
	}

	private static void evaluateSingleApplianceDyn(File applianceSrcDir)
			throws Exception {
		final String appliance = applianceSrcDir.getName();
		successes = trials = 0;
		for (File dataFile : applianceSrcDir.listFiles()) {
			counter = 0;
			RHAI.identifyAppliance(dataFile, new DataHandler<String>() {

				@Override
				public void handle(String toBeHandled) {
					counter++;
					int weight = counter * counter;
					trials += weight;
					globalTrials += weight;
					if (appliance.equals(toBeHandled)) {
						successes += weight;
						globalSuccesses += weight;
					}
				}
			});
		}
		String perc = computePerc(successes, trials);
		System.out.println("The identification of " + applianceSrcDir.getName()
				+ " resulted in a " + perc + " of success");
	}

	private static String computePerc(int successes, int trials) {
		float perc = ((float) successes / trials) * 100;
		return String.format("%.2f", perc) + "%";
	}

	@EntryPoint(id = { "-h", "-help", "-man" }, description = "prints a simple help", args = { "" })
	public static void printHelp(Object[] args) {
		HelpPrinter
				.print("Welcome to RHAI validation system: it allows to evaluate RHAI general performances",
						PerformanceEvaluator.class);
	}
}
