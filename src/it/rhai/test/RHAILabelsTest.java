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
				RHAILabels.forName("low").distanceFrom(
						RHAILabels.forName("medium")));
	}

	@Test
	public void test2() throws Exception {
		ArrayList<RHAILabel> labels = new ArrayList<RHAILabel>();
		labels.add(RHAILabels.forName("low"));
		labels.add(RHAILabels.forName("high"));
		assertEquals(RHAILabels.forName("medium"), RHAILabels.smooth(labels));
	}

}
