package it.rhai.routines.entries.performance;

import it.rhai.routines.entries.RHAI;
import it.rhai.util.DataHandler;
import it.rhai.util.DirectoryOnlyFilter;

import java.io.File;

public class DynamicPerformanceEvaluator extends PerformanceEvaluator {

	private static int counter = 0;

	public static void evaluate(File dir, DataHandler<String> logger)
			throws Exception {
		evaluateDirDyn(dir, logger);
		printOverallPerformance(logger);
	}

	private static void evaluateDirDyn(File dir, DataHandler<String> logger)
			throws Exception {
		for (File applianceSrcDir : dir.listFiles(new DirectoryOnlyFilter())) {
			evaluateSingleApplianceDyn(applianceSrcDir, logger);
		}
	}

	private static void evaluateSingleApplianceDyn(File applianceSrcDir,
			DataHandler<String> logger) throws Exception {
		final String appliance = applianceSrcDir.getName();
		successes = trials = 0;
		for (File dataFile : applianceSrcDir.listFiles()) {
			counter = 0;
			RHAI.identify(dataFile, new DataHandler<String>() {

				@Override
				public void handle(String toBeHandled) {
					counter++;
					int weight = counter;
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
		logger.handle("The identification of " + applianceSrcDir.getName()
				+ " resulted in a " + perc + " of success");
	}
}
