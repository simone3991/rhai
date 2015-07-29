package it.rhai.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import it.rhai.model.RHAILabels;
import it.rhai.model.RHAILabels.RHAILabel;

import org.junit.Test;

public class RHAILabelsTest {

	@Test
	public void test() throws Exception {
		assertEquals(
				1,
				RHAILabels.forName("ll").distanceFrom(
						RHAILabels.forName("lm")));
	}

	@Test
	public void test2() throws Exception {
		ArrayList<RHAILabel> labels = new ArrayList<RHAILabel>();
		labels.add(RHAILabels.forName("ll"));
		labels.add(RHAILabels.forName("mm"));
		assertEquals(RHAILabels.forName("lm"), RHAILabels.smooth(labels));
	}

}
