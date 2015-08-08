package it.rhai.test;

import it.distanciable.sequence.Sequence;
import it.rhai.model.RHAILabelEnum;
import it.rhai.model.RHAILabelEnum.RHAILabel;
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

	private static final String LIB_DIRECTORY = "data/lib";
	private HashMap<String, ArrayList<Sequence<RHAILabel>>> appliances = new HashMap<String, ArrayList<Sequence<RHAILabel>>>();

	public TestSettings() {
		this.loadLib();
	}

	@Override
	/*
	 * (non-Javadoc) s
	 * 
	 * @see it.rhai.settings.RHAISettings#getLib()
	 */
	public Collection<Sequence<RHAILabel>> getLib() {
		ArrayList<Sequence<RHAILabel>> lib = new ArrayList<Sequence<RHAILabel>>();
		for (ArrayList<Sequence<RHAILabel>> list : appliances.values()) {
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
	public String getAppliance(Sequence<RHAILabel> recognizedSequence) {
		for (String appliance : appliances.keySet()) {
			for (Sequence<RHAILabel> sequence : appliances.get(appliance)) {
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
			appliances.put(appliance, new ArrayList<Sequence<RHAILabel>>());
			for (File file : subDir.listFiles()) {
				try {
					appliances.get(appliance).add(getSequenceFromFile(file));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private Sequence<RHAILabel> getSequenceFromFile(File file)
			throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = reader.readLine();
		reader.close();
		return toSequence(line);
	}

	private Sequence<RHAILabel> toSequence(String line) {
		String[] elements = line.trim().split("-");
		Sequence<RHAILabel> sequence = new Sequence<RHAILabel>(elements.length);
		for (String string : elements) {
			try {
				sequence.addElement(RHAILabelEnum.valueOf(string.trim()));
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

	@Override
	public String getRHAIroot() {
		// TODO Auto-generated method stub
		return null;
	}
}
