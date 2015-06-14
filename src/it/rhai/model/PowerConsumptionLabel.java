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
	low(0), medium(1), high(2);
	private int value;

	private PowerConsumptionLabel(int value) {
		this.value = value;
	}

	@Override
	public PowerConsumptionLabel getCopy() {
		return PowerConsumptionLabel.valueOf(this.name());
	}

	@Override
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
	public int distanceFrom(PowerConsumptionLabel antherLabel,
			Distanciator<PowerConsumptionLabel> distanciator) {
		return distanciator.computeDistance(this, antherLabel);
	}

	@Override
	public int getMaximumDistance() {
		return 2;
	}

}
