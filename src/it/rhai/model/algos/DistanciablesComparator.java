package it.rhai.model.algos;

import it.distanciable.Distanciable;

import java.util.Comparator;

/**
 * This implementation of {@link Comparator} interface provides comparison
 * between instances of {@link Distanciable}. An object is considered greater
 * than another if the return of {@link Distanciable#getAbsoluteDistance()}
 * results greater than the other object returned value. Hence, it is
 * <i>mandatory</i> for those object to properly implement that method: a
 * <code>null</code> returned value will not be accepted. On the other hand,
 * this comparator does accept <code>null</code> references as parameters: a
 * non- <code>null</code> reference will always be considered greater than a
 * <code>null</code> one, while two <code>null</code> references will be
 * considered as equal
 * 
 * @author simone
 *
 * @param <T>
 */
public class DistanciablesComparator<T> implements Comparator<Distanciable<T>> {

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Distanciable<T> one, Distanciable<T> another) {
		if (one == null && another != null) {
			return -1;
		}
		if (one != null && another == null) {
			return 1;
		}
		if (one == null && another != null) {
			return 0;
		}
		return Integer.valueOf(one.getAbsoluteDistance()).compareTo(
				Integer.valueOf(another.getAbsoluteDistance()));
	}
}
