package it.rhai.simulation.abstraction;

import it.distanciable.Distanciable;
import it.distanciable.sequences.Sequence;

import java.io.File;
import java.io.IOException;

/**
 * {@link Abstractor} interface represents any kind of object able to extract a
 * {@link Sequence} from a source data file
 * 
 * @author simone
 *
 * @param <T>: the type of sequence to be extracted
 */
public interface Abstractor<T extends Distanciable<T>> {

	/**
	 * Extracts the sequence from the data file
	 * 
	 * @param data
	 *            : the source data file
	 * @return: the extracted sequence
	 * @throws IOException 
	 */
	public Sequence<T> buildSequence(File data) throws IOException;

}
