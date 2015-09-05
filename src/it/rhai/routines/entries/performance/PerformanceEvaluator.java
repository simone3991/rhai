package it.rhai.routines.entries.performance;

import it.rhai.routines.EntryPoint;
import it.rhai.routines.HelpPrinter;
import it.rhai.util.Loggers;

import java.io.File;
import java.io.IOException;

public class PerformanceEvaluator {

	@EntryPoint(id = { "-s", "-static" }, description = "returns the static performance", args = { "a dataset directory" })
	public static void evaluateDirStatic(String[] args) throws Exception {
		File dir = new File(args[0]);
		StaticPerformanceEvaluator.evaluate(dir, Loggers.getLogger("stdout"));
	}

	@EntryPoint(id = { "-d", "-dynamic" }, description = "returns the dynamic performance", args = { "a dataset directory" })
	public static void evaluateDirDyn(String[] args) throws Exception {
		File dir = new File(args[0]);
		DynamicPerformanceEvaluator.evaluate(dir, Loggers.getLogger("stdout"));
	}

	@EntryPoint(args = { "a dataset directory" }, description = "returns the cross-validation", id = {
			"-c", "-cross" })
	public static void crossValidates(String[] args) throws IOException {
		File dir = new File(args[0]);
		CrossValidator.validate(dir, Loggers.getLogger("stdout"));
	}

	@EntryPoint(id = { "-h", "-help", "-man" }, description = "prints a simple help", args = { "" })
	public static void printHelp(Object[] args) {
		HelpPrinter
				.print("Welcome to RHAI validation system: it allows to evaluate RHAI general performances",
						PerformanceEvaluator.class);
	}
}
