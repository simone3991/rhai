package it.rhai.test;

import static org.junit.Assert.assertEquals;
import it.distanciable.sequences.Sequence;
import it.distanciable.sequences.SequenceRecognizer;
import it.rhai.identification.ApplianceIdentifier;
import it.rhai.identification.Sequencer;
import it.rhai.model.PowerConsumptionLabel;
import it.rhai.settings.ConcreteSettings;
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
				sequence.addElement(PowerConsumptionLabel.high);
				sequence.addElement(PowerConsumptionLabel.medium);
				sequence.addElement(PowerConsumptionLabel.high);
			} catch (Exception e) {
			}
			return sequence;
		}
	};
	
	private static ApplianceIdentifier identifier = new ApplianceIdentifier(
			sequencer, new SequenceRecognizer<PowerConsumptionLabel>(null));

	static {
		SettingsKeeper.setSettings(new ConcreteSettings());
	}

	@Test
	public void testIdentify() {
		assertEquals("lib/forno", identifier.identify(null));
	}

}
