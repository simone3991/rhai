package it.rhai.settings;

import it.rhai.model.PowerConsumptionLabel;

import java.util.ArrayList;
import java.util.HashMap;

import model.Sequence;

public class ConcreteSettings implements Settings{

	@Override
	public HashMap<String, Integer> getAvailableLevels() {
		HashMap<String, Integer> levels = new HashMap<String, Integer>();
		levels.put("low", 0);
		levels.put("medium", 1);
		levels.put("high", 2);
		return levels;
	}

	@Override
	public ArrayList<Sequence<PowerConsumptionLabel>> getLib() {
		return null;
	}

	@Override
	public double getTolerance() {
		return 0;
	}

}
