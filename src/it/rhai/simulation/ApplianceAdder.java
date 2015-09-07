package it.rhai.simulation;

import it.distanciable.sequence.Sequence;
import it.rhai.model.PowerMeasure;
import it.rhai.model.RHAILabelEnum.RHAILabel;
import it.rhai.settings.SettingsKeeper;
import it.rhai.simulation.abstraction.AbstractorHandler;
import it.rhai.simulation.abstraction.CumulativeAbstractor;
import it.rhai.simulation.abstraction.JTSAAbstractor;
import it.rhai.simulation.abstraction.JTSARenderedAbstractor;
import it.rhai.simulation.reading.RedirectingReader;
import it.rhai.util.DataHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Calendar;

/* TODO: fix static references to resources location */
public class ApplianceAdder {

	private ArrayList<PowerMeasure> data = new ArrayList<PowerMeasure>();
	private int nextData = 0;
	private Sequence<RHAILabel> sequence;
	private String name = Calendar.getInstance().getTime().toString().trim();

	public void addAppliance(String appliance, File sourceFile,
			File applianceIcon) throws Exception {
		this.loadData(sourceFile);
		RedirectingReader<PowerMeasure> reader = new RedirectingReader<PowerMeasure>(
				new AbstractorHandler<PowerMeasure, RHAILabel>(
						new CumulativeAbstractor<RHAILabel>(new JTSAAbstractor(
								new JTSARenderedAbstractor())),
						new DataHandler<Sequence<RHAILabel>>() {

							@Override
							public void handle(Sequence<RHAILabel> toBeHandled) {
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
			Files.copy(
					applianceIcon.toPath(),
					Paths.get(SettingsKeeper.getSettings().getRHAIroot() + "/"
							+ "data/icons/" + appliance + ".png"),
					StandardCopyOption.REPLACE_EXISTING);
		}
	}

	private int computeToBeAbstracted() {
		return SettingsKeeper.getSettings().getTAbstraction()
				/ PowerMeasure.computeSamplingTime(data);
	}

	private void doAddAppliance(String appliance) throws IOException {
		File dir = new File(SettingsKeeper.getSettings().getRHAIroot() + "/"
				+ "data/lib/" + appliance);
		if ((!dir.exists()) || !(dir.isDirectory())) {
			dir.mkdirs();
		}
		ObjectOutputStream writer = new ObjectOutputStream(
				new FileOutputStream(new File(SettingsKeeper.getSettings()
						.getRHAIroot()
						+ "/"
						+ "data/lib/"
						+ dir.getName()
						+ "/" + name + ".lib")));
		writer.writeObject(sequence);
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

	public void addAppliance(String name, String name2, File dataFile,
			File object) throws Exception {
		this.name = name;
		this.addAppliance(name2, dataFile, object);
	}
}
