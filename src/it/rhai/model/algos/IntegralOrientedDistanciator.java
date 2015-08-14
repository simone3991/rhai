package it.rhai.model.algos;

import it.distanciable.Distanciable;
import it.distanciable.Distanciator;
import it.distanciable.sequence.Sequence;

/**
 * This implementation of {@link Distanciator} interface computes the distance
 * between two sequences according to their integral value. Which means that two
 * sequences "1 - 1 - 3" and "1 - 3 - 1" will be considered exactly the same by
 * this algorithm. Please note that, to compute the integral value, sequenced
 * element must properly implement {@link Distanciable#getAbsoluteDistance()}
 * method: <code>null</code> values will not be accepted
 * 
 * @author simone
 *
 * @param <T>
 */
public class IntegralOrientedDistanciator<T> implements
		Distanciator<Sequence<T>> {

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.distanciable.Distanciator#computeDistance(java.lang.Object,
	 * java.lang.Object)
	 */
	public int computeDistance(Sequence<T> from, Sequence<T> to) {
		return Math.abs(computeArea(from) - computeArea(to));
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.distanciable.Distanciator#computeMaximumDistance(java.lang.Object,
	 * java.lang.Object)
	 */
	public int computeMaximumDistance(Sequence<T> from, Sequence<T> to) {
		Sequence<T> longestSequence = findBiggest(from, to);
		return computeArea(longestSequence);
	}

	private Sequence<T> findBiggest(Sequence<T> from, Sequence<T> to) {
		return computeArea(from) > computeArea(to) ? from : to;
	}

	@SuppressWarnings("unchecked")
	private int computeArea(Sequence<T> sequence) {
		int area = 0;
		for (T element : sequence) {
			area += element != null ? ((Distanciable<T>) element)
					.getAbsoluteDistance() : 0;
		}
		return area;
	}

}
