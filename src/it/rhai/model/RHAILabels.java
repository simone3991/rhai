package it.rhai.model;

import it.distanciable.Copiable;
import it.distanciable.Distanciable;
import it.distanciable.Distanciator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;

public class RHAILabels {

	public static final class RHAILabel implements Distanciable<RHAILabel>,
			Copiable<RHAILabel> {

		private int ordinal;
		private String name;

		private RHAILabel(String name, int ordinal) {
			this.name = name;
			this.ordinal = ordinal;
		}

		@Override
		public RHAILabel getCopy() {
			return new RHAILabel(name, ordinal);
		}

		@Override
		public int distanceFrom(RHAILabel another) {
			if (another == null) {
				return this.getMaximumDistance();
			}
			return Math.abs(ordinal - another.ordinal);
		}

		@Override
		public int distanceFrom(RHAILabel anotherLabel,
				Distanciator<RHAILabel> distanciator) {
			return distanciator.computeDistance(this, anotherLabel);
		}

		@Override
		public int getMaximumDistance() {
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (RHAILabel label : RHAILabels.labels) {
				list.add(label.ordinal);
			}
			return Collections.max(list) - Collections.min(list);
		}

		@Override
		public boolean equals(Object obj) {
			RHAILabel other = (RHAILabel) obj;
			return name.equals(other.name);
		}

		@Override
		public String toString() {
			return name;
		}
	}

	private static HashSet<RHAILabel> labels = new HashSet<RHAILabel>();

	static {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File(
					"data/parameters/levels.properties")));
			String line = prop.getProperty("label");
			String[] labelsString = line.split(",");
			for (int i = 0; i < labelsString.length; i++) {
				labels.add(new RHAILabel(labelsString[i], i + 1));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private RHAILabels() {

	}

	public static RHAILabel forName(String label) {
		for (RHAILabel rhaiLabel : labels) {
			if (rhaiLabel.name.equals(label)) {
				return rhaiLabel;
			}
		}
		return null;
	}

	public static RHAILabel smooth(Collection<RHAILabel> labels) {
		int smooth = getAverageValue(labels);
		return labelOf(smooth);
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
