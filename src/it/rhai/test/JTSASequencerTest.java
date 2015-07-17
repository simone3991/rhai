package it.rhai.test;

import it.rhai.abstraction.Abstractor;
import it.rhai.abstraction.JTSAAbstractor;
import it.rhai.model.PowerConsumptionLabel;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class JTSASequencerTest {

	private static Abstractor<PowerConsumptionLabel> abstractor = new TestingAbstractor<PowerConsumptionLabel>();
	private static JTSAAbstractor sequencer = new JTSAAbstractor(abstractor);

	@Test
	public void testBuildSequence() throws IOException {
		sequencer.buildSequence(new File("testing.dat"));
	}

}
