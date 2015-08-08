package it.rhai.settings;

import it.distanciable.sequence.Sequence;
import it.distanciable.sequence.SequenceRecognizer;
import it.rhai.model.RHAILabelEnum.RHAILabel;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This interface provides all necessary informations to provide appliances
 * identification over a sequence of {@link RHAILabel} instances
 * 
 * @author simone
 *
 */
public interface RHAIdentificationSettings {

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
	 * {@link RHAILabel} instances
	 * 
	 * @param sequence
	 *            : the applsiance's sequence
	 * @return: a string representation for the appliance
	 */
	public String getAppliance(Sequence<RHAILabel> sequence);

	/**
	 * Returns the available library of {@link Sequence} instances. Theese
	 * sequences will be used for appliance identification as templates, which
	 * means that {@link SequenceRecognizer#save(ArrayList)} will have this lib
	 * as a parameter.
	 * 
	 * @return: the whole set of possible sequences
	 */
	public Collection<Sequence<RHAILabel>> getLib();
	
	/**
	 * Returns every identifiable appliance
	 * 
	 * @return: a collection of strings representing each appliance
	 */
	public Collection<String> getAvailableAppliances();
}
