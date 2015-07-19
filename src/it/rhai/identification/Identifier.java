package it.rhai.identification;

import java.util.ArrayList;

import it.distanciable.sequences.Sequence;
import it.distanciable.sequences.SequenceRecognizer;
import it.rhai.model.PowerConsumptionLabel;
import it.rhai.reading.DataHandler;
import it.rhai.settings.SettingsKeeper;

public class Identifier implements DataHandler<Sequence<PowerConsumptionLabel>> {

	private SequenceRecognizer<PowerConsumptionLabel> recognizer;

	public Identifier() {
		this.recognizer = new SequenceRecognizer<PowerConsumptionLabel>(null);
		recognizer.save(SettingsKeeper.getSettings().getLib());
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
		SettingsKeeper.getSettings().getDebugLogger()
				.println("active appliance: " + appliance);
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
