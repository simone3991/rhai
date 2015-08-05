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
				RHAILabelEnum.valueOf("ll").distanceFrom(
						RHAILabelEnum.valueOf("lm")));
	}

	@Test
	public void testSmooth() throws Exception {
		ArrayList<RHAILabel> labels = new ArrayList<RHAILabel>();
		labels.add(RHAILabelEnum.valueOf("ll"));
		labels.add(RHAILabelEnum.valueOf("mm"));
		assertEquals(RHAILabelEnum.valueOf("lm"), RHAILabelEnum.smooth(labels));
	}

	@Test
	public void testValues() throws Exception {
		assertEquals(5, RHAILabelEnum.values().length);
	}

}
