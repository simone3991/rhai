package it.rhai.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.distanciable.Distanciator;
import it.distanciable.sequences.Sequence;
import it.rhai.model.PowerConsumptionLabel;
import it.rhai.model.RealTimeDistanciator;

import org.junit.Test;

public class RealTimeDistanciatorTest {

	private Distanciator<Sequence<PowerConsumptionLabel>> distanciator = new RealTimeDistanciator<PowerConsumptionLabel>();

	@Test
	public void testComputeDistance() {
		Sequence<PowerConsumptionLabel> sequence1 = new Sequence<PowerConsumptionLabel>(
				3);
		sequence1.addElement(PowerConsumptionLabel.high);
		sequence1.addElement(PowerConsumptionLabel.mediumhigh);
		sequence1.addElement(PowerConsumptionLabel.low);
		Sequence<PowerConsumptionLabel> sequence2 = new Sequence<PowerConsumptionLabel>(
				2);
		sequence2.addElement(PowerConsumptionLabel.high);
		sequence2.addElement(PowerConsumptionLabel.mediumhigh);
		assertEquals(0, sequence1.distanceFrom(sequence2, distanciator));
		assertTrue(sequence1.distanceFrom(sequence2) > sequence1.distanceFrom(
				sequence2, distanciator));
	}

}
