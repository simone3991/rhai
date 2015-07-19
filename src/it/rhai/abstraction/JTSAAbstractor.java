package it.rhai.abstraction;

import it.bmi.jtsa.test.JTSATester;
import it.distanciable.sequences.Sequence;
import it.rhai.model.PowerConsumptionLabel;
import it.rhai.settings.SettingsKeeper;
import it.rhai.test.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * This implementation of {@link Abstractor} interface uses the JTSA Framework
 * to extract a sequence of elementary patterns from a data file
 * 
 * @author simone
 *
 */
public class JTSAAbstractor implements Abstractor<PowerConsumptionLabel> {

	private Abstractor<PowerConsumptionLabel> outputAbstractor;

	public JTSAAbstractor(Abstractor<PowerConsumptionLabel> outputAbstractor) {
		this.outputAbstractor = outputAbstractor;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.rhai.identification.Sequencer#buildSequence(java.io.File)
	 */
	public Sequence<PowerConsumptionLabel> buildSequence(File data)
			throws IOException {
		try {
			FileUtils xmlModifier = new FileUtils(new File("data/template.xml"));
			File xmlModified = xmlModifier.replaceLabel(data.getPath(), "$TMP");
			xmlModified.deleteOnExit();
			JTSATester.run(xmlModified .getName());
		} catch (Exception e) {
			e.printStackTrace(SettingsKeeper.getSettings().getDebugLogger());
			;
		}
		File tmpFile = new File("data/output.jtsa");
		tmpFile.deleteOnExit();
		return outputAbstractor.buildSequence(tmpFile);
	}
}
