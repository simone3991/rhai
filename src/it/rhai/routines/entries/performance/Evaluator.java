package it.rhai.routines.entries.performance;

import it.rhai.util.DataHandler;

public class Evaluator {

	protected static int successes, globalSuccesses = 0;
	protected static int trials, globalTrials = 0;

	protected static String computePerc(int successes, int trials) {
		float perc = ((float) successes / trials) * 100;
		return String.format("%.2f", perc) + "%";
	}

	protected static void printOverallPerformance(DataHandler<String> logger) {
		String perc = computePerc(globalSuccesses, globalTrials);
		logger.handle("The overall performance computation resulted in a "
				+ perc + " of success");
	}
}
