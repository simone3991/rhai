package it.rhai.test;

import java.io.File;
import java.io.IOException;

import it.rhai.model.PowerMeasure;
import it.rhai.reading.Reader;
import it.rhai.simulation.ReaderInvoker;

public class ReaderInvokerTest {

	public static void main(String[] args) throws IOException {
		ReaderInvoker invoker = new ReaderInvoker(new File("testing.dat"), new Reader<PowerMeasure>() {
			int i=0;
			@Override
			public void read(PowerMeasure value) {
				System.out.println("read data "+(i+1));
				i++;
			}
		}, 1000);
	}
}
