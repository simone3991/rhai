package it.rhai.model;

import java.util.Collections;
import java.util.HashMap;

import util.distanciables.Distanciable;
import util.distanciables.Distanciator;

public class PowerConsumptionLabel implements
		Distanciable<PowerConsumptionLabel> {

	private static final HashMap<String, Integer> LEVELS = new HashMap<String, Integer>();
	
	static{
		LEVELS.put("low", 0);
		LEVELS.put("medium", 1);
		LEVELS.put("high", 2);
	}

	private String level;

	public PowerConsumptionLabel(String level) {
		this.level = level;
	}

	@Override
	public PowerConsumptionLabel getCopy() {
		return new PowerConsumptionLabel(level);
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
}
