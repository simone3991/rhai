package it.rhai.test.model;

import it.distanciable.Copiable;
import it.distanciable.Distanciable;
import it.distanciable.Distanciator;

public class IntegerValue implements Distanciable<IntegerValue>,
		Copiable<IntegerValue> {

	private Integer value;

	public IntegerValue(int value) {
		this.value = new Integer(value);
	}

	@Override
	public IntegerValue getCopy() {
		return new IntegerValue(value);
	}

	@Override
	public int distanceFrom(IntegerValue anotherInteger) {
		if (anotherInteger==null) {
			return 1;
		}
		return Math.abs(value - anotherInteger.value);
	}

	@Override
	public int distanceFrom(IntegerValue anotherInteger,
			Distanciator<IntegerValue> distanciator) {
		return distanciator.computeDistance(this, anotherInteger);
	}

	@Override
	public int getMaximumDistance() {
		return -1;
	}

}
