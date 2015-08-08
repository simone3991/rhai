package it.rhai.simulation.identification;

import it.distanciable.sequence.Sequence;
import it.distanciable.sequence.SequenceRecognizer;
import it.rhai.model.RHAILabelEnum.RHAILabel;
import it.rhai.model.RealTimeDistanciator;
import it.rhai.settings.RHAIdentificationSettings;
import it.rhai.util.DataHandler;

import java.util.ArrayList;

public class Identifier implements DataHandler<Sequence<RHAILabel>> {

	private SequenceRecognizer<RHAILabel> recognizer;
	private DataHandler<String> outputDisplayer;
	private RHAIdentificationSettings environment;

	public Identifier(DataHandler<String> outputDisplayer,
			RHAIdentificationSettings environment) {
		this.outputDisplayer = outputDisplayer;
		this.recognizer = new SequenceRecognizer<RHAILabel>(
				new RealTimeDistanciator<RHAILabel>());
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
