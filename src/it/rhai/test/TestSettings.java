package it.rhai.test;

import it.distanciable.sequences.Sequence;
import it.rhai.model.PowerConsumptionLabel;
import it.rhai.settings.RHAISettings;
import it.rhai.util.DataHandler;
import it.rhai.util.Loggers;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class TestSettings implements RHAISettings {

	private static final String LIB_DIRECTORY = "lib";
	private HashMap<String, ArrayList<Sequence<PowerConsumptionLabel>>> appliances = new HashMap<String, ArrayList<Sequence<PowerConsumptionLabel>>>();

	public TestSettings() {
		this.loadLib();
	}

	@Override
	/*
	 * (non-Javadoc) s
	 * 
	 * @see it.rhai.settings.RHAISettings#getLib()
	 */
	public Collection<Sequence<PowerConsumptionLabel>> getLib() {
		ArrayList<Sequence<PowerConsumptionLabel>> lib = new ArrayList<Sequence<PowerConsumptionLabel>>();
		for (ArrayList<Sequence<PowerConsumptionLabel>> list : appliances
				.values()) {
			lib.addAll(list);
		}
		return lib;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.rhai.settings.RHAISettings#getTolerance()
	 */
	public double getMinimumLikelihood() {
		return -10;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.rhai.settings.RHAISettings#getAppliance(it.distanciable.sequences.
	 * Sequence)
	 */
	public String getAppliance(
			Sequence<PowerConsumptionLabel> recognizedSequence) {
		for (String appliance : appliances.keySet()) {
			for (Sequence<PowerConsumptionLabel> sequence : appliances
					.get(appliance)) {
				if (sequence.equals(recognizedSequence)) {
					return appliance.substring(appliance.lastIndexOf("/") + 1);
				}
			}
		}
		return null;
	}

	private void loadLib() {
		File directory = new File(LIB_DIRECTORY);
		for (File subDir : directory.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		})) {
			String appliance = subDir.toString();
			appliances.put(appliance,
					new ArrayList<Sequence<PowerConsumptionLabel>>());
			for (File file : subDir.listFiles()) {
				try {
					appliances.get(appliance).add(getSequenceFromFile(file));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private Sequence<PowerConsumptionLabel> getSequenceFromFile(File file)
			throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = reader.readLine();
		reader.close();
		return toSequence(line);
	}

	private Sequence<PowerConsumptionLabel> toSequence(String line) {
		String[] elements = line.trim().split("-");
		Sequence<PowerConsumptionLabel> sequence = new Sequence<PowerConsumptionLabel>(
				elements.length);
		for (String string : elements) {
			try {
				sequence.addElement(PowerConsumptionLabel.valueOf(string.trim()));
			} catch (IllegalArgumentException exception) {
				exception.printStackTrace();
			}
		}
		return sequence;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.rhai.settings.RHAISettings#getTAbstraction()
	 */
	public int getTAbstraction() {
		return 13;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.rhai.settings.RHAISettings#getDebugLogger()
	 */
	public DataHandler<String> getDebugLogger() {
		return Loggers.getLogger("stdout");
	}

	@Override
	public Collection<String> getAvailableAppliances() {
		return appliances.keySet();
	}

	@Override
	public Image getIcon(String appliance) {
		return null;
	}
}
