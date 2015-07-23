package it.rhai.settings;

import it.distanciable.sequences.Sequence;
import it.distanciable.sequences.SequenceRecognizer;
import it.rhai.model.PowerConsumptionLabel;
import it.rhai.util.DataHandler;

import java.awt.Image;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.ImageIcon;

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
	public Collection<Sequence<PowerConsumptionLabel>> getLib();

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

	/**
	 * Returns the exact number of data to be redirected to the Abstractor
	 * instance
	 * 
	 * @return
	 */
	public int getTAbstraction();

	/**
	 * Returns a stream where log any debug message
	 * 
	 * @return: a {@link PrintStream} where messages should be printed
	 */
	public DataHandler<String> getDebugLogger();

	/**
	 * Returns every identifiable appliance
	 * 
	 * @return: a collection of strings representing each appliance
	 */
	public Collection<String> getAvailableAppliances();

	/**
	 * Returns an icon representation for a certain appliance
	 * 
	 * @param appliance
	 *            : the appliance whose representation is required
	 * 
	 * @return: an {@link ImageIcon} instance representing that appliance
	 */
	public Image getIcon(String appliance);
}
