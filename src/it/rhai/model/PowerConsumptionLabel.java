package it.rhai.model;

import it.distanciable.Distanciable;
import it.distanciable.Distanciator;
import it.rhai.settings.SettingsKeeper;

import java.util.Collections;
import java.util.HashMap;

public class PowerConsumptionLabel implements
		Distanciable<PowerConsumptionLabel> {

	private static final HashMap<String, Integer> LEVELS;

	static {
		LEVELS = SettingsKeeper.getInstance().getAvailableLevels();
	}

	private String level;

	public PowerConsumptionLabel(String level) throws Exception {
		if (LEVELS.containsKey(level)) {
			this.level = level;
		} else {
			throw new Exception();
		}
	}

	@Override
	public PowerConsumptionLabel getCopy() {
		try {
			return new PowerConsumptionLabel(level);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public int distanceFrom(PowerConsumptionLabel another) {
		int distance = LEVELS.get(level).intValue()
				- LEVELS.get(another.level).intValue();
		if (distance < 0) {
			distance = -distance;
		}
		return distance;
	}

	@Override
	public int distanceFrom(PowerConsumptionLabel another,
			Distanciator<PowerConsumptionLabel> distanciator) {
		return distanciator.computeDistance(this, another);
	}

	@Override
	public int getMaximumDistance() {
		return Collections.max(LEVELS.values());
	}
	
	@Override
	public String toString() {
		return level;
	}
}
