package it.rhai.test;

import it.rhai.model.RHAILabels.RHAILabel;
import it.rhai.settings.SettingsKeeper;
import it.rhai.simulation.abstraction.Abstractor;
import it.rhai.simulation.abstraction.JTSAAbstractor;
import it.rhai.simulation.abstraction.JTSARenderedAbstractor;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class JTSASequencerTest {

	private static Abstractor<RHAILabel> abstractor = new JTSARenderedAbstractor();
	private static JTSAAbstractor sequencer = new JTSAAbstractor(abstractor);

	static{
		SettingsKeeper.setSettings(new TestSettings());
	}
	
	@Test
	public void testBuildSequence() throws IOException {
		sequencer.buildSequence(new File("data/testing.dat"));
	}

}
