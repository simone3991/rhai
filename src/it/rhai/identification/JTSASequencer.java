package it.rhai.identification;

import it.bmi.jtsa.test.JTSATester;
import it.distanciable.sequences.Sequence;
import it.rhai.model.PowerConsumptionLabel;
import it.rhai.test.XMLAdjuster;

import java.io.File;

public class JTSASequencer implements Sequencer<PowerConsumptionLabel> {

	@Override
	public Sequence<PowerConsumptionLabel> buildSequence(File data) {
		try {
			XMLAdjuster.adjust(data.getPath());
			JTSATester.run("sequencer.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
