package it.rhai.simulation.identification;

import it.distanciable.sequences.Sequence;
import it.distanciable.sequences.SequenceRecognizer;
import it.rhai.model.PowerConsumptionLabel;
import it.rhai.model.RealTimeDistanciator;
import it.rhai.settings.SettingsKeeper;
import it.rhai.util.DataHandler;

import java.util.ArrayList;

public class Identifier implements DataHandler<Sequence<PowerConsumptionLabel>> {

	private SequenceRecognizer<PowerConsumptionLabel> recognizer;
	private DataHandler<String> outputDisplayer;

	public Identifier(DataHandler<String> outputDisplayer) {
		this.outputDisplayer = outputDisplayer;
		this.recognizer = new SequenceRecognizer<PowerConsumptionLabel>(
				new RealTimeDistanciator<PowerConsumptionLabel>());
		recognizer
				.save((ArrayList<Sequence<PowerConsumptionLabel>>) SettingsKeeper
						.getSettings().getLib());
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.rhai.reading.DataHandler#handle(java.lang.Object)
	 */
	public void handle(Sequence<PowerConsumptionLabel> data) {
		Sequence<PowerConsumptionLabel> real = doIdentify(data).get(0);
		String appliance = SettingsKeeper.getSettings().getAppliance(real);
		outputDisplayer.handle(appliance);
	}

	private ArrayList<Sequence<PowerConsumptionLabel>> doIdentify(
			Sequence<PowerConsumptionLabel> data) {
		prepareRecognizer(data);
		return recognizer.recognize(SettingsKeeper.getSettings()
				.getMinimumLikelihood());
	}

	private void prepareRecognizer(Sequence<PowerConsumptionLabel> data) {
		ArrayList<Sequence<PowerConsumptionLabel>> dataReceived = new ArrayList<Sequence<PowerConsumptionLabel>>(
				1);
		dataReceived.add(data);
		recognizer.receiveMessage(dataReceived);
	}

}
