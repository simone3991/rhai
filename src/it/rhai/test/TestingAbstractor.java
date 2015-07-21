package it.rhai.test;

import it.distanciable.Distanciable;
import it.distanciable.sequences.Sequence;
import it.rhai.settings.SettingsKeeper;
import it.rhai.simulation.abstraction.Abstractor;

import java.io.File;

public class TestingAbstractor<T extends Distanciable<T>> implements
		Abstractor<T> {

	private int counter = 1;

	@Override
	public Sequence<T> buildSequence(File data) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				SettingsKeeper.getSettings().getDebugLogger()
						.handle("\t\t\t\tbuilt sequence n°: " + counter);
				counter++;
			}
		});
		thread.start();
		return null;
	}

}
