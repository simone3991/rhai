package it.rhai.simulation.abstraction;

import it.bmi.jtsa.test.JTSATester;
import it.distanciable.sequences.Sequence;
import it.rhai.model.RHAILabelEnum.RHAILabel;
import it.rhai.util.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * This implementation of {@link Abstractor} interface uses the JTSA Framework
 * to extract a sequence of elementary patterns ({@link RHAILabel} instances)
 * from a data file containing power consumption informations
 * 
 * @author simone
 *
 */
public class JTSAAbstractor implements Abstractor<RHAILabel> {

	private Abstractor<RHAILabel> outputAbstractor;

	public JTSAAbstractor(Abstractor<RHAILabel> outputAbstractor) {
		this.outputAbstractor = outputAbstractor;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.rhai.identification.Sequencer#buildSequence(java.io.File)
	 */
	public Sequence<RHAILabel> buildSequence(File data) throws IOException {
		try {
			File xmlModified = FileUtils.replaceLabel(data.getPath(), "$TMP",
					new File("data/template.xml"));
			xmlModified.deleteOnExit();
			JTSATester.run(xmlModified.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		File tmpFile = new File("data/output.jtsa");
		tmpFile.deleteOnExit();
		return outputAbstractor.buildSequence(tmpFile);
	}
}
