package it.rhai.test;

import static org.junit.Assert.*;
import it.rhai.model.PowerConsumptionLabel;
import it.rhai.settings.DebugSettings;
import it.rhai.settings.SettingsKeeper;

import org.junit.Test;

public class PowerConsumptionLabelTest {

	private static PowerConsumptionLabel low;
	private static PowerConsumptionLabel high;
	private static PowerConsumptionLabel medium;

	static {
		SettingsKeeper.setInstance(new DebugSettings());
		try {
			low = new PowerConsumptionLabel("low");
			high = new PowerConsumptionLabel("high");
			medium = new PowerConsumptionLabel("medium");
		} catch (Exception e) {
		}
	}

	@Test
	public void testDistanceFrom() {
		assertTrue(low.distanceFrom(low) == 0);
		assertTrue(low.distanceFrom(medium) < low.distanceFrom(high));
	}

}
