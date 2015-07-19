package it.rhai.simulation.abstraction;

import it.distanciable.Distanciable;
import it.distanciable.sequences.Sequence;

import java.io.File;
import java.io.IOException;

/**
 * This DP Decorator oriented implementation of {@link Abstractor} interface
 * redirects the effective abstraction to a different concrete, but gives as
 * output a sequence built as the sum of every sequence built since creation
 * 
 * @author simone
 *
 * @param <T>: the type of the sequence
 */
public class CumulativeAbstractor<T extends Distanciable<T>> implements
		Abstractor<T> {

	private Abstractor<T> abstractor;
	private Sequence<T> sequence = new Sequence<T>(0);

	/**
	 * Creates a new instance of this class
	 * 
	 * @param abstractor
	 *            : the effective abstractor that will handle the file
	 */
	public CumulativeAbstractor(Abstractor<T> abstractor) {
		this.abstractor = abstractor;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.rhai.abstraction.Abstractor#buildSequence(java.io.File)
	 */
	public Sequence<T> buildSequence(File data) throws IOException {
		Sequence<T> result = abstractor.buildSequence(data);
		Sequence<T> sequence = new Sequence<T>(this.sequence.getSequence()
				.size() + result.getSequence().size());
		for (T element : this.sequence.getSequence()) {
			sequence.addElement(element);
		}
		for (T element : result.getSequence()) {
			sequence.addElement(element);
		}
		this.sequence = sequence;
		return this.sequence;
	}

}
