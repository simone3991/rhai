package it.rhai.model.algos;

import java.util.Collections;
import java.util.Comparator;

import it.distanciable.Distanciable;
import it.distanciable.Distanciator;
import it.distanciable.sequence.Sequence;

public class MaximumOrientedDistanciator<T> implements
		Distanciator<Sequence<T>> {

	@Override
	public int computeDistance(Sequence<T> from, Sequence<T> to) {
		return Math.abs(computeMax(from) - computeMax(to));
	}

	@Override
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
