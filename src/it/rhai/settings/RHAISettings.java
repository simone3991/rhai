package it.rhai.settings;

import it.distanciable.sequences.Sequence;
import it.distanciable.sequences.SequenceRecognizer;
import it.rhai.model.PowerConsumptionLabel;

import java.util.ArrayList;

/**
 * {@link RHAISettings} represents a generic interface through which all needed
 * informations about the framework can be taken
 * 
 * @author simone
 *
 */
public interface RHAISettings {

	/**
	 * Returns the available library of {@link Sequence} instances. Theese
	 * sequences will be used for appliance identification as templates, which
	 * means that {@link SequenceRecognizer#save(ArrayList)} will have this lib
	 * as a parameter.
	 * 
	 * @return: the whole set of possible sequences
	 */
	public ArrayList<Sequence<PowerConsumptionLabel>> getLib();

	/**
	 * Retuns the tolerance of the identification. This value will be used to
	 * decide whether or not a set of data should be identified as an existing
	 * household appliance. In other words, it will be parameter of
	 * {@link SequenceRecognizer#recognize(double)}
	 * 
	 * @return
	 */
	public double getMinimumLikelihood();

	/**
	 * Returns the proper appliance for a specified sequence of
	 * {@link PowerConsumptionLabel} instances
	 * 
	 * @param sequence
	 *            : the applsiance's sequence
	 * @return: a string representation for the appliance
	 */
	public String getAppliance(Sequence<PowerConsumptionLabel> sequence);

}
