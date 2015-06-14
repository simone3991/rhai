package it.rhai.test;

import static org.junit.Assert.*;
import it.rhai.model.PowerConsumptionLabel;

import org.junit.Test;

public class PowerConsumptionLabelTest {

	@Test
	public void testGetCopy() {
		assertEquals("low", PowerConsumptionLabel.low.getCopy().toString());
	}

	@Test
	public void testDistanceFromPowerConsumptionLabel() {
		assertEquals(1, PowerConsumptionLabel.low.distanceFrom(PowerConsumptionLabel.medium));
	}

	@Test
	public void testGetMaximumDistance() {
		assertEquals(2, PowerConsumptionLabel.high.getMaximumDistance());
	}

}
