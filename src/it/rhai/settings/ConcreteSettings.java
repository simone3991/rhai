package it.rhai.settings;

import it.distanciable.sequences.Sequence;
import it.rhai.model.PowerConsumptionLabel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ConcreteSettings implements RHAISettings {

	private static final String LEVELS_PROPERTIES_FILE = "levels.properties";
	private HashMap<Sequence<PowerConsumptionLabel>, String> appliances = new HashMap<Sequence<PowerConsumptionLabel>, String>();

	@Override
	public HashMap<String, Integer> getAvailableLevels() {
		File file = new File(LEVELS_PROPERTIES_FILE);
		try {
			return getLevelsFromFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Sequence<PowerConsumptionLabel>> getLib() {
		ArrayList<Sequence<PowerConsumptionLabel>> lib = new ArrayList<Sequence<PowerConsumptionLabel>>();
		Sequence<PowerConsumptionLabel> sequence1 = new Sequence<PowerConsumptionLabel>(
				2);
		lib.add(sequence1);
		Sequence<PowerConsumptionLabel> sequence2 = new Sequence<PowerConsumptionLabel>(
				2);
		lib.add(sequence2);
		try {
			sequence1.addElement(new PowerConsumptionLabel("low power"));
			sequence1.addElement(new PowerConsumptionLabel("low power"));
			sequence2.addElement(new PowerConsumptionLabel("high power"));
			sequence2.addElement(new PowerConsumptionLabel("high power"));
			this.appliances.put(sequence1, "load1");
			this.appliances.put(sequence2, "load2");
		} catch (Exception e) {
		}
		return lib;
	}

	@Override
	public double getTolerance() {
		return 0;
	}

	@Override
	public String getAppliance(Sequence<PowerConsumptionLabel> sequence) {
		for (Sequence<PowerConsumptionLabel> libSequence : appliances.keySet()) {
			if (libSequence.toString().compareTo(sequence.toString()) == 0) {
				return appliances.get(libSequence);
			}
		}
		return null;
	}
	
	private HashMap<String, Integer> getLevelsFromFile(File file)
			throws FileNotFoundException, IOException {
		HashMap<String, Integer> levels = new HashMap<String, Integer>();
		String[] labels = getLabels(file);
		for (int i = 0; i < labels.length; i++) {
			levels.put(labels[i], i);
		}
		return levels;
	}

	private String[] getLabels(File file) throws FileNotFoundException,
			IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		reader.readLine();
		String line = reader.readLine();
		reader.close();
		String[] labels = toLabels(line);
		return labels;
	}

	private String[] toLabels(String line) {
		line = line.split("=")[1];
		String[] labels = line.split(",");
		return labels;
	}

}
