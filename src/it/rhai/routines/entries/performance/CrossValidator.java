package it.rhai.routines.entries.performance;

import it.rhai.routines.entries.RHAI;
import it.rhai.util.DataHandler;
import it.rhai.util.DirectoryOnlyFilter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class CrossValidator extends PerformanceEvaluator {

	private static String globalRecognized;
	private static int counter;
	private static HashMap<String, HashMap<String, Integer>> crossMap = new HashMap<String, HashMap<String, Integer>>();

	public static void validate(File dir, DataHandler<String> logger)
			throws IOException {
		crossValidate(dir);
	}

	private static void crossValidate(File dir) throws IOException {
		for (File applianceSrcDir : dir.listFiles(new DirectoryOnlyFilter())) {
			counter = 0;
			crossValidateSingleAppliance(applianceSrcDir);
			String real = applianceSrcDir.getName();
			printApplianceResult(real);
		}
	}

	private static void printApplianceResult(String real) {
		HashMap<String, Integer> map = crossMap.get(real);
		boolean failure = false;
		for (String recognized : map.keySet()) {
			if (!real.equals(recognized)) {
				failure = true;
				System.out.println("The validation of " + real + " over "
						+ recognized + " resulted in a "
						+ computePerc(map.get(recognized), counter)
						+ " of failed identifications");
			}
		}
		if (!failure) {
			System.out.println("No failure has been detected for appliance: "
					+ real);
		}
	}

	private static void crossValidateSingleAppliance(File applianceSrcDir)
			throws IOException {
		String appliance = applianceSrcDir.getName();
		crossMap.put(appliance, new HashMap<String, Integer>());
		for (File dataFile : applianceSrcDir.listFiles()) {
			counter++;
			RHAI.identify(dataFile, new DataHandler<String>() {

				@Override
				public void handle(String toBeHandled) {
					globalRecognized = toBeHandled;
				}
			});
			if (!crossMap.get(appliance).containsKey(globalRecognized)) {
				crossMap.get(appliance).put(globalRecognized, 0);
			}
			if (!appliance.equals(globalRecognized)) {
				crossMap.get(appliance).put(globalRecognized,
						crossMap.get(appliance).get(globalRecognized) + 1);
			}
		}
	}
}
