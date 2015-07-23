package it.rhai.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.distanciable.sequences.Sequence;
import it.rhai.model.PowerConsumptionLabel;

import org.junit.Test;

public class SettingsTest {

	private static TestSettings settings = new TestSettings();

	@Test
	public void testGetLib() {
		assertTrue(settings.getLib().size() == 4);
	}

	@Test
	public void testGetAppliance() {
		Sequence<PowerConsumptionLabel> sequence = new Sequence<PowerConsumptionLabel>(
				3);
		sequence.addElement(PowerConsumptionLabel.low);
		sequence.addElement(PowerConsumptionLabel.low);
		assertEquals("forno", settings.getAppliance(sequence));
	}

}
