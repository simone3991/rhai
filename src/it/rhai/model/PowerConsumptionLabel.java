package it.rhai.model;

import it.distanciable.Copiable;
import it.distanciable.Distanciable;
import it.distanciable.Distanciator;

/**
 * {@link PowerConsumptionLabel} enumeration represents any available level of
 * power consumption. Each level is identified by a textual representation, a
 * string obtained by calling {@link PowerConsumptionLabel#name()}
 * 
 * @author simone
 *
 */
public enum PowerConsumptionLabel implements
		Distanciable<PowerConsumptionLabel>, Copiable<PowerConsumptionLabel> {
	low(0), mediumlow(1), medium(3), mediumhigh(3), high(4);
	private int value;

	private PowerConsumptionLabel(int value) {
		this.value = value;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.distanciable.Copiable#getCopy()
	 */
	public PowerConsumptionLabel getCopy() {
		return PowerConsumptionLabel.valueOf(this.name());
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.distanciable.Distanciable#distanceFrom(java.lang.Object)
	 */
	public int distanceFrom(PowerConsumptionLabel anotherLabel) {
		if (anotherLabel == null) {
			return this.value;
		}
		int distance = this.value - anotherLabel.value;
		if (distance < 0) {
			distance = -distance;
		}
		return distance;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.distanciable.Distanciable#distanceFrom(java.lang.Object,
	 * it.distanciable.Distanciator)
	 */
	public int distanceFrom(PowerConsumptionLabel antherLabel,
			Distanciator<PowerConsumptionLabel> distanciator) {
		return distanciator.computeDistance(this, antherLabel);
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.distanciable.Distanciable#getMaximumDistance()
	 */
	public int getMaximumDistance() {
		return 2;
	}

}
