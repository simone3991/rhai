package it.rhai.test;

import it.rhai.model.PowerMeasure;
import it.rhai.model.RHAILabelEnum.RHAILabel;
import it.rhai.settings.SettingsKeeper;
import it.rhai.simulation.ReaderInvoker;
import it.rhai.simulation.abstraction.AbstractorHandler;
import it.rhai.simulation.abstraction.CumulativeAbstractor;
import it.rhai.simulation.abstraction.JTSAAbstractor;
import it.rhai.simulation.abstraction.JTSARenderedAbstractor;
import it.rhai.simulation.identification.Identifier;
import it.rhai.simulation.reading.RedirectingReader;
import it.rhai.util.DataHandler;

import java.io.File;
import java.io.IOException;

public class ReaderInvokerTest {

	static {
		SettingsKeeper.setSettings(new TestSettings());
	}

	public static void main(String[] args) throws IOException {
		ReaderInvoker invoker = new ReaderInvoker(
				new File("data/testing.dat"),
				new RedirectingReader<PowerMeasure>(
						new AbstractorHandler<PowerMeasure, RHAILabel>(
								new CumulativeAbstractor<RHAILabel>(
										new JTSAAbstractor(
												new JTSARenderedAbstractor())),
								new Identifier(new DataHandler<String>() {

									@Override
									public void handle(String toBeHandled) {
										System.out.println(toBeHandled);

									}
								}))));
		invoker.start();
	}
}