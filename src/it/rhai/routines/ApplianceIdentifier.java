package it.rhai.routines;

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
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ApplianceIdentifier {

	private static ArrayList<PowerMeasure> data = new ArrayList<PowerMeasure>();
	private static int nextData = 0;
	private static String appliance;

	@EntryPoint(id = { "-d", "-dynamic" }, description = "identifies an appliance over time", args = { "data file" })
	public static void identifyRealTime(String[] args) throws IOException {
		loadData(new File(args[0]));
		RedirectingReader<PowerMeasure> reader = new RedirectingReader<PowerMeasure>(
				new AbstractorHandler<PowerMeasure, RHAILabel>(
						new CumulativeAbstractor<RHAILabel>(new JTSAAbstractor(
								new JTSARenderedAbstractor())), new Identifier(
								new DataHandler<String>() {

									@Override
									public void handle(String toBeHandled) {
										System.out
												.println("most likely identification: "
														+ toBeHandled);
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

	@EntryPoint(id = { "-s", "-static" }, description = "returns the final identification", args = { "data file" })
	public static void identifyFinal(String[] args) throws IOException {
		loadData(new File(args[0]));
		RedirectingReader<PowerMeasure> reader = new RedirectingReader<PowerMeasure>(
				new AbstractorHandler<PowerMeasure, RHAILabel>(
						new CumulativeAbstractor<RHAILabel>(new JTSAAbstractor(
								new JTSARenderedAbstractor())), new Identifier(
								new DataHandler<String>() {

									@Override
									public void handle(String toBeHandled) {
										appliance = toBeHandled;
									}
								}, SettingsKeeper.getSettings())));
		int length = SettingsKeeper.getSettings().getTAbstraction()
				/ (PowerMeasure.computeSamplingTime(data));
		reader.setMaxLength(length);
		while (nextData < data.size()) {
			reader.read(data.get(nextData));
			nextData++;
		}
		System.out.println("most likely identification: " + appliance);
	}

	@EntryPoint(id = { "-h", "-help", "-man" }, description = "prints a simple help", args = { "" })
	public static void help(Object... args) {
		HelpPrinter.print("Welcome to RHAI identification system",
				ApplianceIdentifier.class);
	}

	private static void loadData(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = reader.readLine();
		while (line != null && line.trim() != "") {
			data.add(PowerMeasure.parsePowerMeasure(line));
			line = reader.readLine();
		}
		reader.close();
	}
}
