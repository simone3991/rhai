package it.rhai.settings;

import it.rhai.model.PowerConsumptionLabel;

import java.util.ArrayList;
import java.util.HashMap;

import model.Sequence;

public class SettingsKeeper implements Settings{

	private static Settings instance;

	private SettingsKeeper() {
		super();
	}

	public static Settings getInstance() {
		return instance;
	}
	
	public static void setInstance(Settings instance) {
		SettingsKeeper.instance = instance;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see it.rhai.settings.Settings#getAvailableLevels()
	 */
	public HashMap<String, Integer> getAvailableLevels() {
		return instance.getAvailableLevels();
	}
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see it.rhai.settings.Settings#getLib()
	 */
	public ArrayList<Sequence<PowerConsumptionLabel>> getLib() {
		return instance.getLib();
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see it.rhai.settings.Settings#getTolerance()
	 */
	public double getTolerance() {
		return instance.getTolerance();
	}
}
