package it.rhai.routines.entries;

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
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class RHAI {

	public static void identify(final File data,
			final DataHandler<String> resultHandler) throws IOException {
		// Thread thread = new Thread(new Runnable() {
		// public void run() {
		// try {
		// identifyAppliance(data, resultHandler);
		// } catch (IOException e) {
		// throw new RuntimeException(e);
		// }
		// }
		// });
		// thread.start();
		identifyAppliance(data, resultHandler);
	}

	private static void identifyAppliance(File dataFile,
			DataHandler<String> finalHandler) throws IOException {
		int nextData;
		ArrayList<PowerMeasure> data = loadData(dataFile);
		nextData = 0;

		DataHandler<Sequence<RHAILabel>> realIdentifier = new Identifier(
				finalHandler, SettingsKeeper.getSettings());

		DataHandler<Collection<PowerMeasure>> realAbstractor = new AbstractorHandler<PowerMeasure, RHAILabel>(
				new CumulativeAbstractor<RHAILabel>(new JTSAAbstractor(
						new JTSARenderedAbstractor())), realIdentifier);

		RedirectingReader<PowerMeasure> reader = new RedirectingReader<PowerMeasure>(
				realAbstractor);

		int length = SettingsKeeper.getSettings().getTAbstraction()
				/ (PowerMeasure.computeSamplingTime(data));
		reader.setMaxLength(length);
		while (nextData < data.size()) {
			reader.read(data.get(nextData));
			nextData++;
		}
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
}
