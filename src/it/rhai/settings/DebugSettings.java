package it.rhai.settings;

import it.distanciable.sequences.Sequence;
import it.rhai.model.PowerConsumptionLabel;

import java.util.ArrayList;
import java.util.HashMap;

public class DebugSettings implements RHAISettings {

	private HashMap<Sequence<PowerConsumptionLabel>, String> appliances = new HashMap<Sequence<PowerConsumptionLabel>, String>();

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
		ArrayList<Sequence<PowerConsumptionLabel>> lib = new ArrayList<Sequence<PowerConsumptionLabel>>();
		Sequence<PowerConsumptionLabel> sequence1 = new Sequence<PowerConsumptionLabel>(
				2);
		lib.add(sequence1);
		Sequence<PowerConsumptionLabel> sequence2 = new Sequence<PowerConsumptionLabel>(
				2);
		lib.add(sequence2);
		try {
			sequence1.addElement(new PowerConsumptionLabel("low"));
			sequence1.addElement(new PowerConsumptionLabel("low"));
			sequence2.addElement(new PowerConsumptionLabel("high"));
			sequence2.addElement(new PowerConsumptionLabel("high"));
			this.appliances.put(sequence1, "load1");
			this.appliances.put(sequence2, "load2");
		} catch (Exception e) {
		}
		return lib;
	}

	@Override
	public double getTolerance() {
		return 0;
	}

	@Override
	public String getAppliance(Sequence<PowerConsumptionLabel> sequence) {
		for (Sequence<PowerConsumptionLabel> libSequence : appliances.keySet()) {
			if (libSequence.toString().compareTo(sequence.toString()) == 0) {
				return appliances.get(libSequence);
			}
		}
		return "ciao";
	}
}
