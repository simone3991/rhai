package it.rhai.test.model.algos;

import static org.junit.Assert.assertEquals;
import it.distanciable.sequence.Sequence;
import it.rhai.model.algos.IntegralOrientedDistanciator;
import it.rhai.test.model.IntegerValue;

import org.junit.Test;

public class IntegralDistanciatorTest {

	static Sequence<IntegerValue> s1 = new Sequence<IntegerValue>(2);
	static Sequence<IntegerValue> s2 = new Sequence<IntegerValue>(2);
	static IntegralOrientedDistanciator<IntegerValue> distanciator = new IntegralOrientedDistanciator<IntegerValue>();

	static {
		s1.addElement(new IntegerValue(2));
		s1.addElement(new IntegerValue(4));
		s2.addElement(new IntegerValue(4));
		s2.addElement(new IntegerValue(2));
	}

	@Test
	public void testComputeDistance() {
		assertEquals(0, distanciator.computeDistance(s1, s2));
	}

	@Test
	public void testComputeMaximumDistance() {
		assertEquals(6, distanciator.computeMaximumDistance(s1, s2));
		assertEquals(true,
				distanciator.computeMaximumDistance(s1, s2) >= distanciator
						.computeDistance(s1, s2));
	}

}
