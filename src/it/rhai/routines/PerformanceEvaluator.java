package it.rhai.routines;

import it.distanciable.sequence.Sequence;
import it.rhai.model.PowerMeasure;
import it.rhai.model.RHAILabelEnum.RHAILabel;
import it.rhai.settings.SettingsKeeper;
import it.rhai.simulation.abstraction.AbstractorHandler;
import it.rhai.simulation.abstraction.CumulativeAbstractor;
import it.rhai.simulation.abstraction.JTSAAbstractor;
import it.rhai.simulation.abstraction.JTSARenderedAbstractor;
import it.rhai.simulation.identification.Identifier;
import it.rhai.simulation.reading.RedirectingReader;
import it.rhai.util.DataHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

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
		for (File applianceSrcDir : dir.listFiles(new FileFilter() {

			@Override
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.io.FileFilter#accept(java.io.File)
			 */
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		})) {
			evaluateSingleAppliance(applianceSrcDir);
		}
	}

	private static void evaluateDirDyn(File dir) throws Exception {
		for (File applianceSrcDir : dir.listFiles(new FileFilter() {

			@Override
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.io.FileFilter#accept(java.io.File)
			 */
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		})) {
			evaluateSingleApplianceDyn(applianceSrcDir);
		}
	}

	private static void evaluateSingleAppliance(File applianceSrcDir)
			throws Exception {
		final String appliance = applianceSrcDir.getName();
		int nextData;
		successes = trials = 0;
		for (final File dataFile : applianceSrcDir.listFiles()) {
			trials++;
			globalTrials++;
			ArrayList<PowerMeasure> data = loadData(dataFile);
			nextData = 0;
			DataHandler<Sequence<RHAILabel>> identificationHandler = new Identifier(
					new DataHandler<String>() {

						@Override
						public void handle(String toBeHandled) {
							globalRecognized = toBeHandled;
						}
					}, SettingsKeeper.getSettings());

			DataHandler<Collection<PowerMeasure>> RHAIHandler = new AbstractorHandler<PowerMeasure, RHAILabel>(
					new CumulativeAbstractor<RHAILabel>(new JTSAAbstractor(
							new JTSARenderedAbstractor())),
					identificationHandler);

			RedirectingReader<PowerMeasure> reader = new RedirectingReader<PowerMeasure>(
					RHAIHandler);
			int length = SettingsKeeper.getSettings().getTAbstraction()
					/ (PowerMeasure.computeSamplingTime(data));
			reader.setMaxLength(length);
			while (nextData < data.size()) {
				reader.read(data.get(nextData));
				nextData++;
			}
			if (appliance.equals(globalRecognized)) {
				successes++;
				globalSuccesses++;
			}
		}
		String perc = computePerc(successes, trials);
		System.out.println("The identification of " + applianceSrcDir.getName()
				+ " resulted in a " + perc + " of success (" + successes + "/"
				+ trials + ")");
	}

	private static void evaluateSingleApplianceDyn(File applianceSrcDir)
			throws Exception {
		final String appliance = applianceSrcDir.getName();
		int nextData;
		successes = trials = 0;
		for (final File dataFile : applianceSrcDir.listFiles()) {
			counter = 0;
			ArrayList<PowerMeasure> data = loadData(dataFile);
			nextData = 0;
			RedirectingReader<PowerMeasure> reader = new RedirectingReader<PowerMeasure>(
					new AbstractorHandler<PowerMeasure, RHAILabel>(
							new CumulativeAbstractor<RHAILabel>(
									new JTSAAbstractor(
											new JTSARenderedAbstractor())),
							new Identifier(new DataHandler<String>() {

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
							}, SettingsKeeper.getSettings())));
			int length = SettingsKeeper.getSettings().getTAbstraction()
					/ (PowerMeasure.computeSamplingTime(data));
			reader.setMaxLength(length);
			while (nextData < data.size()) {
				reader.read(data.get(nextData));
				nextData++;
			}
		}
		String perc = computePerc(successes, trials);
		System.out.println("The identification of " + applianceSrcDir.getName()
				+ " resulted in a " + perc + " of success");
	}

	private static String computePerc(int successes, int trials) {
		float perc = ((float) successes / trials) * 100;
		return String.format("%.2f", perc) + "%";
	}

	private static ArrayList<PowerMeasure> loadData(File file)
			throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = reader.readLine();
		ArrayList<PowerMeasure> data = new ArrayList<PowerMeasure>();
		while (line != null && line.trim() != "") {
			data.add(PowerMeasure.parsePowerMeasure(line));
			line = reader.readLine();
		}
		reader.close();
		return data;
	}

	@EntryPoint(id = { "-h", "-help", "-man" }, description = "prints a simple help", args = { "" })
	public static void printHelp(Object[] args) {
		HelpPrinter
				.print("Welcome to RHAI validation system: it allows to evaluate RHAI general performances",
						PerformanceEvaluator.class);
	}
}
