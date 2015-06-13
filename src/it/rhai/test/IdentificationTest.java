package it.rhai.test;

import static org.junit.Assert.assertEquals;
import it.distanciable.sequences.Sequence;
import it.distanciable.sequences.SequenceRecognizer;
import it.rhai.identification.ApplianceIdentifier;
import it.rhai.identification.Sequencer;
import it.rhai.model.PowerConsumptionLabel;
import it.rhai.settings.DebugSettings;
import it.rhai.settings.SettingsKeeper;

import java.io.File;

import org.junit.Test;

public class IdentificationTest {

	private static Sequencer<PowerConsumptionLabel> sequencer = new Sequencer<PowerConsumptionLabel>() {

		@Override
		public Sequence<PowerConsumptionLabel> buildSequence(File data) {
			Sequence<PowerConsumptionLabel> sequence = new Sequence<PowerConsumptionLabel>(
					2);
			try {
				sequence.addElement(new PowerConsumptionLabel("low"));
				sequence.addElement(new PowerConsumptionLabel("medium"));
			} catch (Exception e) {
			}
			return sequence;
		}
	};
	
	private static ApplianceIdentifier identifier = new ApplianceIdentifier(
			sequencer, new SequenceRecognizer<PowerConsumptionLabel>(null));

	static {
		SettingsKeeper.setInstance(new DebugSettings());
	}

	@Test
	public void testIdentify() {
		assertEquals("load1", identifier.identify(null));
	}

}
