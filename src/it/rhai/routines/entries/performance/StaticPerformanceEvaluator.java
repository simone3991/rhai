package it.rhai.routines.entries.performance;

import it.rhai.routines.entries.RHAI;
import it.rhai.util.DataHandler;
import it.rhai.util.DirectoryOnlyFilter;

import java.io.File;

public class StaticPerformanceEvaluator extends Evaluator {

	private static String globalRecognized;
	private static DataHandler<String> logger;

	public static void evaluate(File dir, DataHandler<String> logger)
			throws Exception {
		StaticPerformanceEvaluator.logger = logger;
		evaluateDir(dir);
		printOverallPerformance(logger);
	}

	private static void evaluateDir(File dir) throws Exception {
		for (File applianceSrcDir : dir.listFiles(new DirectoryOnlyFilter())) {
			evaluateSingleAppliance(applianceSrcDir);
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
		logger.handle("The identification of " + applianceSrcDir.getName()
				+ " resulted in a " + perc + " of success (" + successes + "/"
				+ trials + ")");
	}

	private static void handleResultStatic(String appliance) {
		if (appliance.equals(globalRecognized)) {
			successes++;
			globalSuccesses++;
		}
	}
}
