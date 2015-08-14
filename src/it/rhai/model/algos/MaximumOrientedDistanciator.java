package it.rhai.model.algos;

import java.util.Collections;
import java.util.Comparator;

import it.distanciable.Distanciable;
import it.distanciable.Distanciator;
import it.distanciable.sequence.Sequence;

/**
 * This implementation of {@link Distanciator} interface computes the distance
 * between two sequences according to their max values. Which means that two
 * sequences "1 - 0 - 5" and "2 - 5" will have distance 0 according to this
 * algorithm. Please note that, in order to compute max value, it is
 * <i>mandatory</i> for sequenced elements to properly implement
 * {@link Distanciable#getAbsoluteDistance()} method: <code>null</code> values
 * will not be accepted
 * 
 * @author simone
 *
 * @param <T>
 */
public class MaximumOrientedDistanciator<T> implements
		Distanciator<Sequence<T>> {

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.distanciable.Distanciator#computeDistance(java.lang.Object,
	 * java.lang.Object)
	 */
	public int computeDistance(Sequence<T> from, Sequence<T> to) {
		return Math.abs(computeMax(from) - computeMax(to));
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
		return computeMax(findMaxSequence(from, to));
	}

	private Sequence<T> findMaxSequence(Sequence<T> aSequence,
			Sequence<T> another) {
		return computeMax(aSequence) > computeMax(another) ? aSequence
				: another;
	}

	@SuppressWarnings("unchecked")
	private int computeMax(Sequence<T> sequence) {
		Distanciable<T> t = (Distanciable<T>) Collections.max(
				sequence.getSequence(), new Comparator<T>() {

					@Override
					public int compare(T o1, T o2) {
						if (o1 == null && o2 != null) {
							return -1;
						}
						if (o1 != null && o2 == null) {
							return 1;
						}
						if (o1 == null && o2 != null) {
							return 0;
						}
						Distanciable<T> x = (Distanciable<T>) o1;
						Distanciable<T> y = (Distanciable<T>) o2;
						return Integer
								.valueOf(x.getAbsoluteDistance())
								.compareTo(
										Integer.valueOf(y.getAbsoluteDistance()));
					}
				});
		return t != null ? t.getAbsoluteDistance() : 0;
	}
}
