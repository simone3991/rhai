package it.rhai.routines;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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

@Executable(id = "--identify")
public class ApplianceIdentifier {

	private static ArrayList<PowerMeasure> data = new ArrayList<PowerMeasure>();
	private static int nextData = 0;

	public static void execute(String[] args) throws IOException {
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
				/ (computeSamplingTime() / 1000);
		reader.setMaxLength(length);
		while (nextData < data.size()) {
			reader.read(data.get(nextData));
			nextData++;
		}
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

	private static int computeSamplingTime() {
		return (int) ((data.get(data.size() - 1).getDate().getTimeInMillis() - data
				.get(0).getDate().getTimeInMillis()) / ((data.size() - 1)));
	}
}
