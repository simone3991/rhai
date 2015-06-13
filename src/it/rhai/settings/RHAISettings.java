package it.rhai.settings;

import it.distanciable.sequences.Sequence;
import it.rhai.model.PowerConsumptionLabel;

import java.util.ArrayList;
import java.util.HashMap;

public interface RHAISettings {

	public HashMap<String, Integer> getAvailableLevels();

	public ArrayList<Sequence<PowerConsumptionLabel>> getLib();

	public double getTolerance();

	public String getAppliance(Sequence<PowerConsumptionLabel> sequence);

}
