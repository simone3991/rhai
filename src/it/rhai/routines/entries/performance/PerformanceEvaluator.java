package it.rhai.routines.entries.performance;

import it.rhai.util.DataHandler;

/**
 * A superclass for objects whose purpose is to compute performances of the RHAI
 * identification
 * 
 * @author simone
 *
 */
public class PerformanceEvaluator extends Evaluator {

	protected static int successes, globalSuccesses = 0;
	protected static int trials, globalTrials = 0;

	protected static void printOverallPerformance(DataHandler<String> logger) {
		String perc = computePerc(globalSuccesses, globalTrials);
		logger.handle("The overall performance computation resulted in a "
				+ perc + " of success");
	}
}
