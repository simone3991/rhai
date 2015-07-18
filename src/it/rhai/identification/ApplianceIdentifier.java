package it.rhai.identification;

import it.distanciable.sequences.Sequence;
import it.distanciable.sequences.SequenceRecognizer;
import it.rhai.abstraction.Abstractor;
import it.rhai.model.PowerConsumptionLabel;
import it.rhai.settings.RHAISettings;
import it.rhai.settings.SettingsKeeper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class performs the household appliance identification. This is performed
 * extracting {@link Sequence} of {@link PowerConsumptionLabel} from a source
 * data file, and then identifying it using an external library of available
 * sequences. For further informations, see {@link RHAISettings}.
 * 
 * @author simone
 *
 */
public class ApplianceIdentifier {

	private Abstractor<PowerConsumptionLabel> sequencer;
	private SequenceRecognizer<PowerConsumptionLabel> recognizer;

	/**
	 * Creates a new identifier
	 * 
	 * @param sequencer
	 *            : the concrete sequence builder to be used for data
	 *            preprocessing
	 * @param recognizer
	 *            : the instance of {@link SequenceRecognizer} to perform
	 *            identification
	 */
	public ApplianceIdentifier(Abstractor<PowerConsumptionLabel> sequencer,
			SequenceRecognizer<PowerConsumptionLabel> recognizer) {
		this.sequencer = sequencer;
		this.recognizer = recognizer;
	}

	/**
	 * Identifies the correct appliance. The data file is preprocessed to become
	 * a {@link Sequence} of {@link PowerConsumptionLabel} and then this
	 * sequence is identified using an external library to determine available
	 * sequences. For further informations of global settings, like library
	 * elements or the tolerance of the identification, see {@link RHAISettings}
	 * .
	 * 
	 * @param data
	 *            : the file containing power data of the appliance to be
	 *            identified
	 * @return: a string representing the appliance, or null if no appliance
	 *          could be identified
	 * @throws IOException 
	 */
	public String identify(File data) throws IOException {
		recognizer.save(SettingsKeeper.getSettings().getLib());
		recognizer.receiveMessage(getToBeIdentified(data));
		ArrayList<Sequence<PowerConsumptionLabel>> sequence = recognizer
				.recognize(SettingsKeeper.getSettings().getMinimumLikelihood());
		return SettingsKeeper.getSettings().getAppliance(sequence.get(0));
	}

	private ArrayList<Sequence<PowerConsumptionLabel>> getToBeIdentified(
			File data) throws IOException {
		Sequence<PowerConsumptionLabel> sequence = sequencer
				.buildSequence(data);
		ArrayList<Sequence<PowerConsumptionLabel>> list = new ArrayList<Sequence<PowerConsumptionLabel>>();
		list.add(sequence);
		return list;
	}

}
