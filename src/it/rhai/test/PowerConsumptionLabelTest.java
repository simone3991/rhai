package it.rhai.test;

import static org.junit.Assert.assertEquals;
import it.rhai.model.PowerConsumptionLabel;

import org.junit.Test;

public class PowerConsumptionLabelTest {

	@Test
	public void testGetCopy() {
		assertEquals("low", PowerConsumptionLabel.low.getCopy().toString());
	}

	@Test
	public void testDistanceFromPowerConsumptionLabel() {
		assertEquals(
				PowerConsumptionLabel.medium
						.distanceFrom(PowerConsumptionLabel.low),
				PowerConsumptionLabel.low
						.distanceFrom(PowerConsumptionLabel.medium));
	}

	@Test
	public void testGetMaximumDistance() {
		assertEquals(2, PowerConsumptionLabel.high.getMaximumDistance());
	}

}
