package it.rhai.test.model;

import static org.junit.Assert.*;
import it.rhai.model.RHAILabelEnum;
import it.rhai.model.RHAILabelEnum.RHAILabel;

import java.util.ArrayList;

import org.junit.Test;

public class RHAILabelEnumTest {

	@Test
	public void testDistance() throws Exception {
		assertEquals(
				1,
				RHAILabelEnum.valueOf("0000").distanceFrom(
						RHAILabelEnum.valueOf("0001")));
	}

	@Test
	public void testSmooth() throws Exception {
		ArrayList<RHAILabel> labels = new ArrayList<RHAILabel>();
		labels.add(RHAILabelEnum.valueOf("0000"));
		labels.add(RHAILabelEnum.valueOf("0010"));
		assertEquals(RHAILabelEnum.valueOf("0001"),
				RHAILabelEnum.smooth(labels));
	}

	@Test
	public void testValues() throws Exception {
		assertEquals(16, RHAILabelEnum.values().length);
	}

	@Test
	public void testAdd() throws Exception {
		RHAILabel label = RHAILabelEnum.valueOf("0010");
		assertEquals(RHAILabelEnum.valueOf("0100"), label.add(label));
	}

}
