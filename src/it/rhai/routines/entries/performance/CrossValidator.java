package it.rhai.routines.entries.performance;

import it.rhai.routines.entries.RHAI;
import it.rhai.util.DataHandler;
import it.rhai.util.DirectoryOnlyFilter;
import it.rhai.util.StringCouple;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class CrossValidator {

	private static String globalRecognized;
	private static HashMap<StringCouple, Integer> crossMap = new HashMap<StringCouple, Integer>();

	public static void validate(File dir, DataHandler<String> logger)
			throws IOException {
		crossValidate(dir);
		printCrossResult();
	}

	private static void printCrossResult() {
		ArrayList<StringCouple> errors = new ArrayList<StringCouple>();
		errors.addAll(crossMap.keySet());
		Collections.sort(errors);
		if (errors.isEmpty()) {
			System.out.println("Each appliance has been correctly recognized");
			return;
		}
		System.out.println("Theese are the detected errors:");
		for (StringCouple couple : errors) {
			System.out.println("The appliance '" + couple.getFirst()
					+ "' was identified as a '" + couple.getSecond() + "' "
					+ crossMap.get(couple) + " times");
		}
	}

	private static void crossValidate(File dir) throws IOException {
		for (File applianceSrcDir : dir.listFiles(new DirectoryOnlyFilter())) {
			crossValidateSingleAppliance(applianceSrcDir);
		}
	}

	private static void crossValidateSingleAppliance(File applianceSrcDir)
			throws IOException {
		String appliance = applianceSrcDir.getName();
		for (File dataFile : applianceSrcDir.listFiles()) {
			RHAI.identifyAppliance(dataFile, new DataHandler<String>() {

				@Override
				public void handle(String toBeHandled) {
					globalRecognized = toBeHandled;
				}
			});
			if (!globalRecognized.equals(appliance)) {
				StringCouple couple = new StringCouple(appliance,
						globalRecognized);
				if (!crossMap.containsKey(couple)) {
					crossMap.put(couple, 0);
				}
				crossMap.put(couple, crossMap.get(couple) + 1);
			}
		}
	}
}
