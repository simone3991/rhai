package it.rhai.test.model;

import it.distanciable.Copiable;
import it.distanciable.Distanciable;

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
		if (anotherInteger == null) {
			return 1;
		}
		return Math.abs(value - anotherInteger.value);
	}

	@Override
	public int getAbsoluteDistance() {
		return value;
	}

}
