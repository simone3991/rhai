package it.rhai.model;

import it.distanciable.Copiable;
import it.distanciable.Distanciable;
import it.distanciable.Distanciator;

import java.util.Collection;

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
	low(1), mediumlow(2), medium(3), mediumhigh(4), high(5);
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

	/**
	 * Returns the single {@link PowerConsumptionLabel} element that better
	 * approximate the input collection of labels
	 * 
	 * @param labels
	 *            : the collection to be smoothed into a single one label object
	 * @return: the label that better approximate the multiple inputs
	 */
	public static PowerConsumptionLabel smooth(
			Collection<PowerConsumptionLabel> labels) {
		int smooth = getAverageValue(labels);
		return labelOf(smooth);
	}

	private static int getAverageValue(Collection<PowerConsumptionLabel> labels) {
		int sum = 0;
		int smooth;
		for (PowerConsumptionLabel powerConsumptionLabel : labels) {
			sum += powerConsumptionLabel.value;
		}
		smooth = Math.round(sum / labels.size());
		return smooth;
	}

	private static PowerConsumptionLabel labelOf(int value) {
		for (PowerConsumptionLabel powerConsumptionLabel : values()) {
			if (powerConsumptionLabel.value == value) {
				return powerConsumptionLabel;
			}
		}
		return null;
	}

}
