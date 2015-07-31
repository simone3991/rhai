package it.rhai.simulation.identification;

import it.distanciable.sequences.Sequence;
import it.distanciable.sequences.SequenceRecognizer;
import it.rhai.model.RHAILabelEnum.RHAILabel;
import it.rhai.model.RealTimeDistanciator;
import it.rhai.settings.SettingsKeeper;
import it.rhai.util.DataHandler;

import java.util.ArrayList;

public class Identifier implements DataHandler<Sequence<RHAILabel>> {

	private SequenceRecognizer<RHAILabel> recognizer;
	private DataHandler<String> outputDisplayer;

	public Identifier(DataHandler<String> outputDisplayer) {
		this.outputDisplayer = outputDisplayer;
		this.recognizer = new SequenceRecognizer<RHAILabel>(
				new RealTimeDistanciator<RHAILabel>());
		recognizer
				.save((ArrayList<Sequence<RHAILabel>>) SettingsKeeper
						.getSettings().getLib());
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.rhai.reading.DataHandler#handle(java.lang.Object)
	 */
	public void handle(Sequence<RHAILabel> data) {
		Sequence<RHAILabel> real = doIdentify(data).get(0);
		String appliance = SettingsKeeper.getSettings().getAppliance(real);
		outputDisplayer.handle(appliance);
	}

	private ArrayList<Sequence<RHAILabel>> doIdentify(
			Sequence<RHAILabel> data) {
		prepareRecognizer(data);
		return recognizer.recognize(SettingsKeeper.getSettings()
				.getMinimumLikelihood());
	}

	private void prepareRecognizer(Sequence<RHAILabel> data) {
		ArrayList<Sequence<RHAILabel>> dataReceived = new ArrayList<Sequence<RHAILabel>>(
				1);
		dataReceived.add(data);
		recognizer.receiveMessage(dataReceived);
	}

}
