package it.rhai.test;

import it.distanciable.sequences.Sequence;
import it.rhai.abstraction.AbstractorHandler;
import it.rhai.model.PowerConsumptionLabel;
import it.rhai.model.PowerMeasure;
import it.rhai.reading.Reader;
import it.rhai.reading.RedirectingReader;
import it.rhai.simulation.ReaderInvoker;

import java.io.File;
import java.io.IOException;

public class ReaderInvokerTest {

	public static void main(String[] args) throws IOException {
		ReaderInvoker invoker = new ReaderInvoker(new File("testing.dat"),
				new Reader<PowerMeasure>() {
					int i = 0;

					@Override
					public void read(PowerMeasure value) {
						System.out.println("read data " + (i + 1));
						i++;
					}
				}, 1000);
		invoker.start();
		invoker = new ReaderInvoker(
				new File("testing.dat"),
				new RedirectingReader<PowerMeasure>(
						new AbstractorHandler<PowerMeasure, PowerConsumptionLabel>(
								new TestingAbstractor<PowerConsumptionLabel>(),
								new TestingDataHandler<Sequence<PowerConsumptionLabel>>())),
				1000);
		invoker.start();
	}
}