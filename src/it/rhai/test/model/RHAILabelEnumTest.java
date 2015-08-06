package it.rhai.test.model;

import static org.junit.Assert.assertEquals;
import it.rhai.model.RHAILabelEnum;
import it.rhai.model.RHAILabelEnum.RHAILabel;

import java.util.ArrayList;

import org.junit.Test;

public class RHAILabelEnumTest {

	@Test
	public void testDistance() throws Exception {
		assertEquals(
				1,
				RHAILabelEnum.valueOf("000").distanceFrom(
						RHAILabelEnum.valueOf("001")));
	}

	@Test
	public void testSmooth() throws Exception {
		ArrayList<RHAILabel> labels = new ArrayList<RHAILabel>();
		labels.add(RHAILabelEnum.valueOf("000"));
		labels.add(RHAILabelEnum.valueOf("010"));
		assertEquals(RHAILabelEnum.valueOf("001"), RHAILabelEnum.smooth(labels));
	}

	@Test
	public void testValues() throws Exception {
		assertEquals(8, RHAILabelEnum.values().length);
	}

}
