package it.rhai.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.distanciable.Distanciator;
import it.distanciable.sequences.Sequence;
import it.rhai.model.RHAILabelEnum;
import it.rhai.model.RHAILabelEnum.RHAILabel;
import it.rhai.model.RealTimeDistanciator;

import org.junit.Test;

public class RealTimeDistanciatorTest {

	private Distanciator<Sequence<RHAILabel>> distanciator = new RealTimeDistanciator<RHAILabel>();

	@Test
	public void testComputeDistance() {
		Sequence<RHAILabel> sequence1 = new Sequence<RHAILabel>(
				3);
		sequence1.addElement(RHAILabelEnum.valueOf("hh"));
		sequence1.addElement(RHAILabelEnum.valueOf("mm"));
		sequence1.addElement(RHAILabelEnum.valueOf("ll"));
		Sequence<RHAILabel> sequence2 = new Sequence<RHAILabel>(
				2);
		sequence2.addElement(RHAILabelEnum.valueOf("hh"));
		sequence2.addElement(RHAILabelEnum.valueOf("mm"));
		assertEquals(0, sequence1.distanceFrom(sequence2, distanciator));
		assertTrue(sequence1.distanceFrom(sequence2) > sequence1.distanceFrom(
				sequence2, distanciator));
	}

}
