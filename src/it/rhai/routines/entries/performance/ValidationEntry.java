package it.rhai.routines.entries.performance;

import it.rhai.routines.EntryPoint;
import it.rhai.routines.HelpPrinter;
import it.rhai.settings.SettingsKeeper;

import java.io.File;
import java.io.IOException;

public class ValidationEntry {

	@EntryPoint(id = { "-s", "-static" }, description = "returns the static performance", args = { "a dataset directory" })
	public static void evaluateDirStatic(String[] args) throws Exception {
		File dir = new File(args[0]);
		StaticPerformanceEvaluator.evaluate(dir, SettingsKeeper.getSettings()
				.getOutput());
	}

	@EntryPoint(id = { "-d", "-dynamic" }, description = "returns the dynamic performance", args = { "a dataset directory" })
	public static void evaluateDirDyn(String[] args) throws Exception {
		File dir = new File(args[0]);
		DynamicPerformanceEvaluator.evaluate(dir, SettingsKeeper.getSettings()
				.getOutput());
	}

	@EntryPoint(args = { "a dataset directory" }, description = "returns the cross-validation results", id = {
			"-c", "-cross" })
	public static void crossValidates(String[] args) throws IOException {
		File dir = new File(args[0]);
		CrossValidator.validate(dir, SettingsKeeper.getSettings().getOutput());
	}

	@EntryPoint(args = { "a dataset directory" }, description = "computes the average identification time", id = {
			"-t", "-time" })
	public static void computeTime(String[] args) throws Exception {
		File dir = new File(args[0]);
		IdentificationTimeComputer.compute(dir, SettingsKeeper.getSettings()
				.getOutput());
	}

	@EntryPoint(id = { "-h", "-help", "-man" }, description = "prints a simple help", args = { "" })
	public static void printHelp(Object[] args) {
		HelpPrinter
				.print("Welcome to RHAI validation system: it allows to evaluate RHAI general performances",
						ValidationEntry.class, SettingsKeeper.getSettings()
								.getOutput());
	}
}
