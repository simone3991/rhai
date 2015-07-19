package it.rhai.test;

import it.rhai.abstraction.AbstractorHandler;
import it.rhai.abstraction.CumulativeAbstractor;
import it.rhai.abstraction.JTSAAbstractor;
import it.rhai.abstraction.JTSARenderedAbstractor;
import it.rhai.identification.Identifier;
import it.rhai.model.PowerConsumptionLabel;
import it.rhai.model.PowerMeasure;
import it.rhai.reading.RedirectingReader;
import it.rhai.settings.ConcreteSettings;
import it.rhai.settings.SettingsKeeper;
import it.rhai.simulation.ReaderInvoker;

import java.io.File;
import java.io.IOException;

public class ReaderInvokerTest {

	static {
		SettingsKeeper.setSettings(new ConcreteSettings());
	}

	public static void main(String[] args) throws IOException {
		ReaderInvoker invoker = new ReaderInvoker(
				new File("data/testing.dat"),
				new RedirectingReader<PowerMeasure>(
						new AbstractorHandler<PowerMeasure, PowerConsumptionLabel>(
								new CumulativeAbstractor<PowerConsumptionLabel>(
										new JTSAAbstractor(
												new JTSARenderedAbstractor())),
								new Identifier())), 100);
		invoker.start();
	}
}