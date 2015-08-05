package it.rhai.model;

import it.distanciable.Distanciator;
import it.distanciable.sequences.Sequence;

/**
 * This implementation of {@link Distanciator} interface computes the distance
 * between two sequences according to a real-time logic. Imagine you have a
 * given sequence "a - b - c" and that you keep receiving sequences "a", then
 * "a - b", and eventually "a - b - c". Even though your received sequences are
 * not the same as the given one (neither the first or the second one), the
 * distance between them and the given "a - b - c" should constantly be 0,
 * because the received one is perfectly equal to the corresponding part of the
 * given one. Warning: calling {@link #computeDistance(Sequence, Sequence)} will
 * consider the first as the partial sequence and the second parameter as the
 * complete one. Since that, this distance computation is not commutative
 * 
 * @author simone
 *
 * @param <T>: the type of sequence to distanciate
 */
public class RealTimeDistanciator<T> implements Distanciator<Sequence<T>> {

	private Sequence<T> received;
	private Sequence<T> complete;

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.distanciable.Distanciator#computeDistance(java.lang.Object,
	 * java.lang.Object)
	 */
	public int computeDistance(Sequence<T> partial, Sequence<T> complete) {
		this.complete = complete;
		this.received = partial;
		Sequence<T> partOfComplete = cutFutureSequenceToPresent();
		return received.distanceFrom(partOfComplete);
	}

	private Sequence<T> cutFutureSequenceToPresent() {
		Sequence<T> partOfComplete = new Sequence<T>(received.getSequence()
				.size());
		for (int i = 0; i < received.getSequence().size(); i++) {
			try {
				partOfComplete.addElement(complete.get(i));
			} catch (IndexOutOfBoundsException e) {
				partOfComplete.addElement(null);
			}
		}
		return partOfComplete;
	}
}
