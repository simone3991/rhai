package it.rhai.simulation.abstraction;

import it.bmi.jtsa.engine.renderer.basic.implementation.RendererCSV;
import it.distanciable.sequences.Sequence;
import it.rhai.model.RHAILabels;
import it.rhai.model.RHAILabels.RHAILabel;

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
		Abstractor<RHAILabel> {

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.rhai.abstraction.Abstractor#buildSequence(java.io.File)
	 */
	public Sequence<RHAILabel> buildSequence(File data)
			throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(data));
		ArrayList<RHAILabel> labels = getSequence(reader);
		reader.close();
		Sequence<RHAILabel> sequence = toOneLabel(labels);
		return sequence;
	}

	private Sequence<RHAILabel> toOneLabel(
			ArrayList<RHAILabel> labels) {
		Sequence<RHAILabel> sequence = new Sequence<RHAILabel>(
				1);
		sequence.addElement(RHAILabels.smooth(labels));
		return sequence;
	}

	private ArrayList<RHAILabel> getSequence(BufferedReader reader)
			throws IOException {
		String line = reader.readLine();
		ArrayList<RHAILabel> labels = new ArrayList<RHAILabel>();
		while ((line = reader.readLine()) != null) {
			addAllSubSequence(line, labels);
		}
		return labels;
	}

	private void addAllSubSequence(String line,
			ArrayList<RHAILabel> labels) {
		StringTokenizer tokenizer = new StringTokenizer(line, ";");
		int period = -Integer.parseInt(tokenizer.nextToken())
				+ Integer.parseInt(tokenizer.nextToken());
		RHAILabel label = RHAILabels.forName(tokenizer
				.nextToken());
		for (int i = 0; i < period; i++) {
			labels.add(label);
		}
	}
}
