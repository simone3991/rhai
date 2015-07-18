package it.rhai.test;

import it.rhai.abstraction.Abstractor;
import it.rhai.abstraction.JTSAAbstractor;
import it.rhai.abstraction.JTSARenderedAbstractor;
import it.rhai.model.PowerConsumptionLabel;
import it.rhai.settings.ConcreteSettings;
import it.rhai.settings.SettingsKeeper;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class JTSASequencerTest {

	private static Abstractor<PowerConsumptionLabel> abstractor = new JTSARenderedAbstractor();
	private static JTSAAbstractor sequencer = new JTSAAbstractor(abstractor);

	static{
		SettingsKeeper.setSettings(new ConcreteSettings());
	}
	
	@Test
	public void testBuildSequence() throws IOException {
		sequencer.buildSequence(new File("testing.dat"));
	}

}
