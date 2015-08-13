package it.rhai.model.algos;

import it.distanciable.Distanciable;
import it.distanciable.Distanciator;
import it.distanciable.sequence.Sequence;

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
		for (T element : sequence.getSequence()) {
			area += element != null ? ((Distanciable<T>) element)
					.getAbsoluteDistance() : 0;
		}
		return area;
	}

}
