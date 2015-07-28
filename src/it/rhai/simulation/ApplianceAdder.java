package it.rhai.simulation;

import it.distanciable.sequences.Sequence;
import it.rhai.model.PowerConsumptionLabel;
import it.rhai.model.PowerMeasure;
import it.rhai.settings.SettingsKeeper;
import it.rhai.simulation.abstraction.AbstractorHandler;
import it.rhai.simulation.abstraction.CumulativeAbstractor;
import it.rhai.simulation.abstraction.JTSAAbstractor;
import it.rhai.simulation.abstraction.JTSARenderedAbstractor;
import it.rhai.simulation.reading.RedirectingReader;
import it.rhai.util.DataHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Calendar;

public class ApplianceAdder {

	private ArrayList<PowerMeasure> data = new ArrayList<PowerMeasure>();
	private int nextData = 0;
	protected Sequence<PowerConsumptionLabel> sequence;

	public void addAppliance(String appliance, File sourceFile,
			File applianceIcon) throws IOException {
		this.loadData(sourceFile);
		RedirectingReader<PowerMeasure> reader = new RedirectingReader<PowerMeasure>(
				new AbstractorHandler<PowerMeasure, PowerConsumptionLabel>(
						new CumulativeAbstractor<PowerConsumptionLabel>(
								new JTSAAbstractor(new JTSARenderedAbstractor())),
						new DataHandler<Sequence<PowerConsumptionLabel>>() {

							@Override
							public void handle(
									Sequence<PowerConsumptionLabel> toBeHandled) {
								sequence = toBeHandled;
							}
						}));
		reader.setMaxLength(computeToBeAbstracted());
		while (nextData < data.size()) {
			reader.read(data.get(nextData));
			nextData++;
		}
		doAddAppliance(appliance);
		if (applianceIcon != null) {
			Files.copy(applianceIcon.toPath(),
					Paths.get("data/icons/" + appliance + ".png"),
					StandardCopyOption.REPLACE_EXISTING);
		}
	}

	private int computeToBeAbstracted() {
		return SettingsKeeper.getSettings().getTAbstraction()
				/ computeSamplingTime();
	}

	private int computeSamplingTime() {
		return (int) ((data.get(data.size() - 1).getDate().getTimeInMillis() - data
				.get(0).getDate().getTimeInMillis()) / ((data.size() - 1) * 1000));
	}

	private void doAddAppliance(String appliance) throws IOException {
		File dir = new File("lib/" + appliance);
		if ((!dir.exists()) || !(dir.isDirectory())) {
			dir.mkdirs();
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
				"lib/" + dir.getName() + "/"
						+ Calendar.getInstance().getTime().toString().trim()
						+ ".dat")));
		writer.write(sequence.toString());
		writer.close();
	}

	private void loadData(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		while ((line = reader.readLine()) != null) {
			data.add(PowerMeasure.parsePowerMeasure(line));
		}
		reader.close();
	}
}
