package it.rhai.routines.entries.performance;

import it.rhai.routines.entries.RHAI;
import it.rhai.settings.SettingsKeeper;
import it.rhai.util.ArraysUtil;
import it.rhai.util.DataHandler;
import it.rhai.util.DirectoryOnlyFilter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class IdentificationTimeComputer {

	private static ArrayList<String> identified = new ArrayList<String>();

	public static void compute(File dir, DataHandler<String> output)
			throws Exception {
		for (File applianceSrcDir : dir.listFiles(new DirectoryOnlyFilter())) {
			evaluateSingleAppliance(applianceSrcDir, output);
		}
	}

	private static void evaluateSingleAppliance(File applianceSrcDir,
			DataHandler<String> logger) throws Exception {
		String appliance = applianceSrcDir.getName();
		ArrayList<Integer> identificationTimes = new ArrayList<Integer>();
		for (File dataFile : applianceSrcDir.listFiles()) {
			String finalIdentification = identify(dataFile);
			if (finalIdentification.equals(appliance)) {
				identificationTimes.add(ArraysUtil.firstIndexOfOnly(appliance,
						identified.toArray(new String[identified.size()])) + 1);
			}
		}
		Integer averageTime = average(identificationTimes)
				* SettingsKeeper.getSettings().getTAbstraction();
		logger.handle("When not failed, the identification of " + appliance
				+ " required about " + averageTime + " sec to become stable");
	}

	private static Integer average(Collection<Integer> collection) {
		int sum = 0;
		for (Integer integer : collection) {
			sum += integer;
		}
		return sum / collection.size();
	}

	private static String identify(File dataFile) throws IOException {
		identified.removeAll(identified);
		RHAI.identifyAppliance(dataFile, new DataHandler<String>() {

			@Override
			public void handle(String toBeHandled) {
				identified.add(toBeHandled);
			}
		});
		return identified.get(identified.size() - 1);
	}
}
