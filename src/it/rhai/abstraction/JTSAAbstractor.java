package it.rhai.abstraction;

import it.bmi.jtsa.test.JTSATester;
import it.distanciable.sequences.Sequence;
import it.rhai.model.PowerConsumptionLabel;
import it.rhai.test.XMLAdjuster;

import java.io.File;

public class JTSAAbstractor implements Abstractor<PowerConsumptionLabel> {

	@Override
	/*
	 * (non-Javadoc)
	 * @see it.rhai.identification.Sequencer#buildSequence(java.io.File)
	 */
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
