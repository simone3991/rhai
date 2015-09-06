package it.rhai.simulation.identification;

import it.distanciable.Distanciator;
import it.distanciable.sequence.Sequence;
import it.distanciable.sequence.SequenceRecognizer;
import it.rhai.model.RHAILabelEnum.RHAILabel;
import it.rhai.model.algos.RealTimeDistanciator;
import it.rhai.settings.RHAIdentificationSettings;
import it.rhai.util.DataHandler;

import java.util.ArrayList;

/**
 * This implementation of {@link DataHandler} interface handles a
 * {@link Sequence} of {@link RHAILabel} instances by merging it into the
 * most-likely active appliance described by that sequence. The effective
 * handling of that appliance (a simple String) is left to an other instance of
 * {@link DataHandler}.
 * 
 * @author simone
 *
 */
public class Identifier implements DataHandler<Sequence<RHAILabel>> {

	private SequenceRecognizer<RHAILabel> recognizer;
	private DataHandler<String> outputDisplayer;
	private RHAIdentificationSettings environment;
	private Distanciator<Sequence<RHAILabel>> distanciator = new RealTimeDistanciator<RHAILabel>();

	/**
	 * Creates a new instance of this class, with a given output handler for
	 * produced results
	 * 
	 * @param outputDisplayer
	 *            : the instance of {@link DataHandler} that will be responsible
	 *            for outputs
	 * @param environment
	 *            : the {@link RHAIdentificationSettings} environment where
	 *            needed informations will be take from
	 */
	public Identifier(DataHandler<String> outputDisplayer,
			RHAIdentificationSettings environment) {
		this.outputDisplayer = outputDisplayer;
		this.recognizer = new SequenceRecognizer<RHAILabel>(this.distanciator);
		recognizer.save((ArrayList<Sequence<RHAILabel>>) environment.getLib());
		this.environment = environment;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.rhai.reading.DataHandler#handle(java.lang.Object)
	 */
	public void handle(Sequence<RHAILabel> data) {
		Sequence<RHAILabel> real = doIdentify(data).get(0);
		String appliance = environment.getAppliance(real);
		outputDisplayer.handle(appliance);
	}

	private ArrayList<Sequence<RHAILabel>> doIdentify(Sequence<RHAILabel> data) {
		prepareRecognizer(data);
		return recognizer.recognize(environment.getMinimumLikelihood());
	}

	private void prepareRecognizer(Sequence<RHAILabel> data) {
		ArrayList<Sequence<RHAILabel>> dataReceived = new ArrayList<Sequence<RHAILabel>>(
				1);
		dataReceived.add(data);
		recognizer.receiveMessage(dataReceived);
	}
}
