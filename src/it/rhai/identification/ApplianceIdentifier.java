package it.rhai.identification;

import it.distanciable.sequences.Sequence;
import it.distanciable.sequences.SequenceRecognizer;
import it.rhai.model.PowerConsumptionLabel;
import it.rhai.settings.SettingsKeeper;

import java.io.File;
import java.util.ArrayList;

public class ApplianceIdentifier {

	private Sequencer<PowerConsumptionLabel> sequencer;
	private SequenceRecognizer<PowerConsumptionLabel> recognizer;

	public ApplianceIdentifier(Sequencer<PowerConsumptionLabel> sequencer,
			SequenceRecognizer<PowerConsumptionLabel> recognizer) {
		this.sequencer = sequencer;
		this.recognizer = recognizer;
	}

	public String identify(File data) {
		recognizer.save(SettingsKeeper.getInstance().getLib());
		recognizer.receiveMessage(getToBeIdentified(data));
		ArrayList<Sequence<PowerConsumptionLabel>> sequence = recognizer
				.recognize(SettingsKeeper.getInstance().getTolerance());
		return SettingsKeeper.getInstance().getAppliance(sequence.get(0));
	}

	private ArrayList<Sequence<PowerConsumptionLabel>> getToBeIdentified(
			File data) {
		Sequence<PowerConsumptionLabel> sequence = sequencer
				.buildSequence(data);
		ArrayList<Sequence<PowerConsumptionLabel>> list = new ArrayList<Sequence<PowerConsumptionLabel>>();
		list.add(sequence);
		return list;
	}

}
