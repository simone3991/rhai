package it.rhai.routines;

import it.rhai.util.DataHandler;

import java.io.File;
import java.io.IOException;

public class ApplianceIdentifier {

	private static String appliance;

	@EntryPoint(id = { "-d", "-dynamic" }, description = "identifies an appliance over time", args = { "data file" })
	public static void identifyRealTime(String[] args) throws IOException {
		RHAI.identifyAppliance(new File(args[0]), new DataHandler<String>() {

			@Override
			public void handle(String toBeHandled) {
				appliance = toBeHandled;
				System.out.println("most likely identification: " + appliance);
			}
		});
	}

	@EntryPoint(id = { "-s", "-static" }, description = "returns the final identification", args = { "data file" })
	public static void identifyFinal(String[] args) throws IOException {
		RHAI.identifyAppliance(new File(args[0]), new DataHandler<String>() {

			@Override
			public void handle(String toBeHandled) {
				appliance = toBeHandled;
			}
		});
		System.out.println("most likely identification: " + appliance);
	}

	@EntryPoint(id = { "-h", "-help", "-man" }, description = "prints a simple help", args = { "" })
	public static void help(Object... args) {
		HelpPrinter.print("Welcome to RHAI identification system",
				ApplianceIdentifier.class);
	}
}
