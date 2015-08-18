package it.rhai.model.algos;

import it.distanciable.Distanciable;
import it.distanciable.Distanciator;
import it.distanciable.sequence.Sequence;

public class AverageValueOrientedDistanciator<T> implements
		Distanciator<Sequence<T>> {

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.distanciable.Distanciator#computeDistance(java.lang.Object,
	 * java.lang.Object)
	 */
	public int computeDistance(Sequence<T> from, Sequence<T> to) {
		return Math.abs(findAverage(from) - findAverage(to));
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.distanciable.Distanciator#computeMaximumDistance(java.lang.Object,
	 * java.lang.Object)
	 */
	public int computeMaximumDistance(Sequence<T> one, Sequence<T> theOther) {
		int avg1 = findAverage(one);
		int avg2 = findAverage(theOther);
		return avg1 > avg2 ? avg1 : avg2;
	}

	@SuppressWarnings("unchecked")
	private int findAverage(Sequence<T> sequence) {
		int sum = 0;
		for (T element : sequence) {
			sum += element != null ? ((Distanciable<T>) element)
					.getAbsoluteDistance() : 0;
		}
		return sum / sequence.size();
	}
}
