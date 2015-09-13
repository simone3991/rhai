package it.rhai.routines.entries.training;

import it.rhai.routines.entries.RHAI;
import it.rhai.settings.SettingsKeeper;
import it.rhai.util.DataHandler;
import it.rhai.util.DirectoryOnlyFilter;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class AcceptanceLikelihoodOptimizer {

	private static HashMap<Float, Integer> errors = new HashMap<Float, Integer>();
	private static String recognized;

	public static float optimize(File dataset, File fake) throws IOException {
		System.out.printf("%10s %10s %10s %10s", "25%", "50%", "75%", "100%");
		System.out.println();
		for (float acceptanceLikelihood = 0; acceptanceLikelihood < 1; acceptanceLikelihood += 0.05f) {
			modifyProp(acceptanceLikelihood);
			errors.put(acceptanceLikelihood, 0);
			handleDataSet(dataset, acceptanceLikelihood);
			System.out.print("=");
			handleFakeDataset(acceptanceLikelihood, fake);
			System.out.print("=");
		}
		return Collections.max(keysOf(Collections.min(errors.values())));
	}

	private static void modifyProp(float acceptanceLikelihood) {
		SettingsKeeper.getSettings().setMinimumLikelihood(acceptanceLikelihood);
	}

	private static Collection<Float> keysOf(Integer value) {
		Collection<Float> list = new LinkedList<Float>();
		for (Float key : errors.keySet()) {
			if (errors.get(key).equals(value)) {
				list.add(key);
			}
		}
		return list;
	}

	private static void handleDataSet(File dataset, float acceptanceLikelihood)
			throws IOException {
		for (File applianceSourceDir : dataset
				.listFiles(new DirectoryOnlyFilter())) {
			handleDataSetApp(acceptanceLikelihood, applianceSourceDir);
		}
	}

	private static void handleDataSetApp(float acceptanceLikelihood,
			File applianceSourceDir) throws IOException {
		String appliance = applianceSourceDir.getName();
		for (File dataFile : applianceSourceDir.listFiles()) {
			handleFile(dataFile);
			if (!appliance.equals(recognized)) {
				errors.put(acceptanceLikelihood,
						errors.get(acceptanceLikelihood) + 1);
			}
		}
	}

	private static void handleFakeDataset(float acceptanceLikelihood,
			File fakeDataset) throws IOException {
		for (File dataFile : fakeDataset.listFiles()) {
			handleFile(dataFile);
			if (recognized != null) {
				errors.put(acceptanceLikelihood,
						errors.get(acceptanceLikelihood) + 1);
			}
		}
	}

	private static void handleFile(File dataFile) throws IOException {
		RHAI.identify(dataFile, new DataHandler<String>() {

			@Override
			public void handle(String toBeHandled) {
				recognized = toBeHandled;
			}
		});
	}

}
