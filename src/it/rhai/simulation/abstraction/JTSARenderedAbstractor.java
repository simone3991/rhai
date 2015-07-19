package it.rhai.simulation.abstraction;

import it.bmi.jtsa.engine.renderer.basic.implementation.RendererCSV;
import it.distanciable.sequences.Sequence;
import it.rhai.model.PowerConsumptionLabel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * This implementation of {@link Abstractor} interface can extract a
 * {@link Sequence} of {@link PowerConsumptionLabel} from the output file of the
 * JTSA {@link RendererCSV}
 * 
 * @author simone
 *
 */
public class JTSARenderedAbstractor implements
		Abstractor<PowerConsumptionLabel> {

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.rhai.abstraction.Abstractor#buildSequence(java.io.File)
	 */
	public Sequence<PowerConsumptionLabel> buildSequence(File data)
			throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(data));
		ArrayList<PowerConsumptionLabel> labels = getSequence(reader);
		reader.close();
		Sequence<PowerConsumptionLabel> sequence = toOneLabel(labels);
		return sequence;
	}

	private Sequence<PowerConsumptionLabel> toOneLabel(
			ArrayList<PowerConsumptionLabel> labels) {
		Sequence<PowerConsumptionLabel> sequence = new Sequence<PowerConsumptionLabel>(
				1);
		sequence.addElement(PowerConsumptionLabel.smooth(labels));
		return sequence;
	}

	private ArrayList<PowerConsumptionLabel> getSequence(BufferedReader reader)
			throws IOException {
		String line = reader.readLine();
		ArrayList<PowerConsumptionLabel> labels = new ArrayList<PowerConsumptionLabel>();
		while ((line = reader.readLine()) != null) {
			addAllSubSequence(line, labels);
		}
		return labels;
	}

	private void addAllSubSequence(String line,
			ArrayList<PowerConsumptionLabel> labels) {
		StringTokenizer tokenizer = new StringTokenizer(line, ";");
		int period = -Integer.parseInt(tokenizer.nextToken())
				+ Integer.parseInt(tokenizer.nextToken());
		PowerConsumptionLabel label = PowerConsumptionLabel.valueOf(tokenizer
				.nextToken());
		for (int i = 0; i < period; i++) {
			labels.add(label);
		}
	}
}
