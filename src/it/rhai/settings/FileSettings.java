package it.rhai.settings;

import it.distanciable.sequences.Sequence;
import it.rhai.model.RHAILabelEnum;
import it.rhai.model.RHAILabelEnum.RHAILabel;
import it.rhai.util.DataHandler;
import it.rhai.util.Loggers;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Properties;

import javax.imageio.ImageIO;

public class FileSettings implements RHAISettings {

	private Properties properties = new Properties();
	private HashMap<String, ArrayList<Sequence<RHAILabel>>> appliances = new HashMap<String, ArrayList<Sequence<RHAILabel>>>();
	private HashMap<String, Image> icons = new HashMap<String, Image>();

	public FileSettings(File settings) {
		try {
			this.loadParameters(settings);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		return Double.parseDouble(properties
				.getProperty("acceptance_likelihood"));
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.rhai.settings.RHAISettings#getAppliance(it.distanciable.sequences.
	 * Sequence)
	 */
	// TODO: change to Sequence#equals()
	public String getAppliance(Sequence<RHAILabel> recognizedSequence) {
		for (String appliance : appliances.keySet()) {
			for (Sequence<RHAILabel> sequence : appliances.get(appliance)) {
				try {
					if (sequence.toString().compareTo(
							recognizedSequence.toString()) == 0) {
						return appliance;
					}
				} catch (NullPointerException e) {
				}
			}
		}
		return null;
	}

	private void loadLib() {
		File directory = new File(properties.getProperty("lib_root"));
		for (File subDir : directory.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		})) {
			String appliance = subDir.toString();
			appliance = appliance.substring(appliance.lastIndexOf("/") + 1);
			appliances.put(appliance, new ArrayList<Sequence<RHAILabel>>());
			try {
				icons.put(
						appliance,
						ImageIO.read(new File("data/icons/" + appliance
								+ ".png")));
			} catch (IOException e1) {
			}
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

	/*
	 * TODO: move to Sequence#parseSequence()
	 */
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

	private void loadParameters(File settings) throws IOException {
		properties.load(new FileInputStream(settings.getAbsolutePath()));
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.rhai.settings.RHAISettings#getTAbstraction()
	 */
	public int getTAbstraction() {
		return Integer.parseInt(properties.getProperty("t_abstraction"));
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.rhai.settings.RHAISettings#getDebugLogger()
	 */
	public DataHandler<String> getDebugLogger() {
		return Loggers.getLogger(properties.getProperty("debug_printer"));
	}

	@Override
	public Collection<String> getAvailableAppliances() {
		return appliances.keySet();
	}

	@Override
	public Image getIcon(String appliance) {
		return icons.get(appliance);
	}
}
