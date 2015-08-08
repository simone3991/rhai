package it.rhai.test.model;

import static org.junit.Assert.assertTrue;
import it.distanciable.Distanciator;
import it.distanciable.sequence.Sequence;
import it.rhai.model.RealTimeDistanciator;

import org.junit.Test;

public class RealTimeDistanciatorTest {

	private Distanciator<Sequence<IntegerValue>> distanciator = new RealTimeDistanciator<IntegerValue>();

	@Test
	public void testComputeDistance() {
		Sequence<IntegerValue> sequence = new Sequence<IntegerValue>(3);
		sequence.addElement(new IntegerValue(1));
		sequence.addElement(new IntegerValue(2));
		sequence.addElement(new IntegerValue(3));
		Sequence<IntegerValue> sequence2 = new Sequence<IntegerValue>(3);
		sequence.addElement(new IntegerValue(1));
		assertTrue(sequence.distanceFrom(sequence2) > distanciator
				.computeDistance(sequence2, sequence));
		assertTrue(distanciator.computeDistance(sequence, sequence2) != distanciator
				.computeDistance(sequence2, sequence));
		assertTrue(distanciator.computeDistance(sequence, sequence) == 0);
	}

}
