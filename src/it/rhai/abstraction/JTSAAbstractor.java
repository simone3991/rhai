package it.rhai.abstraction;

import it.bmi.jtsa.test.JTSATester;
import it.distanciable.sequences.Sequence;
import it.rhai.model.PowerConsumptionLabel;
import it.rhai.test.FileEditor;

import java.io.File;

/**
 * This implementation of {@link Abstractor} interface uses the JTSA Framework
 * to extract a sequence of elementary patterns from a data file
 * 
 * @author simone
 *
 */
public class JTSAAbstractor implements Abstractor<PowerConsumptionLabel> {

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.rhai.identification.Sequencer#buildSequence(java.io.File)
	 */
	public Sequence<PowerConsumptionLabel> buildSequence(File data) {
		try {
			FileEditor xmlModifier = new FileEditor(new File("template.xml"));
			JTSATester.run(xmlModifier.replaceLabel(data.getPath(), "$TMP")
					.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
