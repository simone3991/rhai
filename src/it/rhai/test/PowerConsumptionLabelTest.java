package it.rhai.test;

import static org.junit.Assert.*;
import it.rhai.model.PowerConsumptionLabel;

import org.junit.Test;

public class PowerConsumptionLabelTest {

	private PowerConsumptionLabel low = new PowerConsumptionLabel("low");
	private PowerConsumptionLabel high = new PowerConsumptionLabel("high");
	private PowerConsumptionLabel medium = new PowerConsumptionLabel("medium");

	@Test
	public void testDistanceFrom() {
		assertTrue(low.distanceFrom(low) == 0);
		assertTrue(low.distanceFrom(medium) < low.distanceFrom(high));
	}

}
