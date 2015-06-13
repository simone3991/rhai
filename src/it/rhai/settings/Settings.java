package it.rhai.settings;

import it.rhai.model.PowerConsumptionLabel;

import java.util.ArrayList;
import java.util.HashMap;

import model.Sequence;

public interface Settings {

	public HashMap<String, Integer> getAvailableLevels();

	public ArrayList<Sequence<PowerConsumptionLabel>> getLib();

	public double getTolerance();

}
