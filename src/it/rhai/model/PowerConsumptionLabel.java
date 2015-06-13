package it.rhai.model;

import it.distanciable.Distanciable;
import it.distanciable.Distanciator;
import it.rhai.settings.RHAISettings;
import it.rhai.settings.SettingsKeeper;

import java.util.Collections;
import java.util.HashMap;

/**
 * This class represents a discrete interval of power consumption level that can
 * be identified by a label. The available labels (and therefore, the number of
 * levels) can be found by calling {@link RHAISettings#getAvailableLevels()}
 * 
 * @author simone
 *
 */
public class PowerConsumptionLabel implements
		Distanciable<PowerConsumptionLabel> {

	private static final HashMap<String, Integer> LEVELS;

	static {
		LEVELS = SettingsKeeper.getSettings().getAvailableLevels();
	}

	private String label;

	/**
	 * Creates a new instance of this class
	 * 
	 * @param label
	 *            : a string representing this power consuption level
	 * @throws Exception: 
	 */
	public PowerConsumptionLabel(String label) throws IllegalArgumentException {
		if (LEVELS.containsKey(label)) {
			this.label = label;
		} else {
			throw new IllegalArgumentException(label+" is not a valid label");
		}
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see it.distanciable.Copiable#getCopy()
	 */
	public PowerConsumptionLabel getCopy() {
		try {
			return new PowerConsumptionLabel(label);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see it.distanciable.Distanciable#distanceFrom(java.lang.Object)
	 */
	public int distanceFrom(PowerConsumptionLabel another) {
		int distance = LEVELS.get(label).intValue()
				- LEVELS.get(another.label).intValue();
		if (distance < 0) {
			distance = -distance;
		}
		return distance;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see it.distanciable.Distanciable#distanceFrom(java.lang.Object, it.distanciable.Distanciator)
	 */
	public int distanceFrom(PowerConsumptionLabel another,
			Distanciator<PowerConsumptionLabel> distanciator) {
		return distanciator.computeDistance(this, another);
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see it.distanciable.Distanciable#getMaximumDistance()
	 */
	public int getMaximumDistance() {
		return Collections.max(LEVELS.values());
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return label;
	}
}
