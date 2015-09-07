package it.rhai.routines.entries.performance;

/**
 * A superclass for objects whose purpose is to compute performances
 * 
 * @author simone
 *
 */
public class Evaluator {

	protected static String computePerc(int successes, int trials) {
		float perc = ((float) successes / trials) * 100;
		return String.format("%.2f", perc) + "%";
	}
}
