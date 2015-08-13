package it.rhai.test.model.algos;

import static org.junit.Assert.*;
import it.distanciable.sequence.Sequence;
import it.rhai.model.algos.MaximumOrientedDistanciator;
import it.rhai.test.model.IntegerValue;

import org.junit.Test;

public class MaxDistanciatorTest {

	static Sequence<IntegerValue> s1 = new Sequence<IntegerValue>(2);
	static Sequence<IntegerValue> s2 = new Sequence<IntegerValue>(2);
	static MaximumOrientedDistanciator<IntegerValue> distanciator = new MaximumOrientedDistanciator<IntegerValue>();

	static {
		s1.addElement(new IntegerValue(1));
		s1.addElement(new IntegerValue(1));
		s2.addElement(new IntegerValue(2));
		s2.addElement(new IntegerValue(4));
	}

	@Test
	public void testComputeDistance() {
		assertEquals(3, distanciator.computeDistance(s1, s2));
	}

	@Test
	public void testComputeMaximumDistance() {
		assertEquals(4, distanciator.computeMaximumDistance(s1, s2));
	}
}
