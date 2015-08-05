package it.rhai.model;

import it.distanciable.Copiable;
import it.distanciable.Distanciable;
import it.distanciable.Distanciator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;

/**
 * This class provides the same behavior of an enumeration of {@link RHAILabel}
 * instances. That meaning that at run-time there will be a fixed number of
 * {@link RHAILabel} instances and no way to add new ones. These instances are
 * staticly injected by reading a .properties file, that must be found in the
 * same folder of the source file.
 * 
 * @author simone
 *
 */
public class RHAILabelEnum {

	private static HashSet<RHAILabel> labels = new HashSet<RHAILabel>();

	private RHAILabelEnum() {
	}

	static {
		init();
	}

	/**
	 * A single label that abstract multiple {@link PowerMeasure} over time
	 * 
	 * @author simone
	 *
	 */
	public static final class RHAILabel implements Distanciable<RHAILabel>,
			Copiable<RHAILabel> {

		private int ordinal;
		private String name;

		private RHAILabel(String name, int ordinal) {
			this.name = name;
			this.ordinal = ordinal;
		}

		@Override
		/*
		 * (non-Javadoc)
		 * 
		 * @see it.distanciable.Copiable#getCopy()
		 */
		public RHAILabel getCopy() {
			return new RHAILabel(name, ordinal);
		}

		@Override
		/*
		 * (non-Javadoc)
		 * 
		 * @see it.distanciable.Distanciable#distanceFrom(java.lang.Object)
		 */
		public int distanceFrom(RHAILabel another) {
			if (another == null) {
				return this.getMaximumDistance();
			}
			return Math.abs(ordinal - another.ordinal);
		}

		@Override
		/*
		 * (non-Javadoc)
		 * 
		 * @see it.distanciable.Distanciable#distanceFrom(java.lang.Object,
		 * it.distanciable.Distanciator)
		 */
		public int distanceFrom(RHAILabel anotherLabel,
				Distanciator<RHAILabel> distanciator) {
			return distanciator.computeDistance(this, anotherLabel);
		}

		@Override
		/*
		 * (non-Javadoc)
		 * 
		 * @see it.distanciable.Distanciable#getMaximumDistance()
		 */
		public int getMaximumDistance() {
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (RHAILabel label : RHAILabelEnum.labels) {
				list.add(label.ordinal);
			}
			return Collections.max(list) - Collections.min(list);
		}

		@Override
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		public boolean equals(Object obj) {
			RHAILabel other = (RHAILabel) obj;
			return name.equals(other.name);
		}

		@Override
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return name;
		}
	}

	/**
	 * Returns the {@link RHAILabel} element that is identified by the given
	 * string, if exists. Otherwise, returns <code>null</code>
	 * 
	 * @param label
	 *            : the textual representation of the desired {@link RHAILabel}
	 * @return: the corresponding instance, or null
	 */
	public static RHAILabel valueOf(String label) {
		for (RHAILabel rhaiLabel : labels) {
			if (rhaiLabel.name.equals(label)) {
				return rhaiLabel;
			}
		}
		return null;
	}

	/**
	 * Smooths multiple {@link RHAILabel} instances into a single one, according
	 * to the naturale distance between them defined by
	 * {@link RHAILabel#distanceFrom(RHAILabel)}
	 * 
	 * @param labels
	 *            : the collection that must be collapsed into a single instance
	 * @return: the {@link RHAILabel} instance that better approximate the given
	 *          collection
	 */
	public static RHAILabel smooth(Collection<RHAILabel> labels) {
		if (labels.size() != 0) {
			int smooth = getAverageValue(labels);
			return labelOf(smooth);
		} else {
			return null;
		}
	}

	/**
	 * Returns each fixed instance collected by this enumeration
	 * 
	 * @return: an array containing the {@link RHAILabel} available instances
	 */
	public static RHAILabel[] values() {
		return labels.toArray(new RHAILabel[labels.size()]);
	}

	private static void init() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File(getFilePath())));
			checkProperties(prop);
			String line = prop.getProperty("label");
			String[] labelsString = line.split(",");
			for (int i = 0; i < labelsString.length; i++) {
				labels.add(new RHAILabel(labelsString[i], i + 1));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void checkProperties(Properties prop) throws IOException,
			FileNotFoundException {
		String loc;
		if ((loc = prop.getProperty("properties-location")) != null) {
			prop.load(new FileInputStream(new File(loc)));
		}
	}

	private static String getFilePath() {
		return "src/" + RHAILabelEnum.class.getName().replace(".", "/")
				+ ".properties";
	}

	private static int getAverageValue(Collection<RHAILabel> labels) {
		int sum = 0;
		int smooth;
		for (RHAILabel label : labels) {
			sum += label.ordinal;
		}
		smooth = Math.round(sum / labels.size());
		return smooth;
	}

	private static RHAILabel labelOf(int ordinal) {
		for (RHAILabel label : labels) {
			if (label.ordinal == ordinal) {
				return label;
			}
		}
		return null;
	}
}
