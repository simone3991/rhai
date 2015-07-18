package it.rhai.test;

import it.distanciable.Distanciable;
import it.distanciable.sequences.Sequence;
import it.rhai.abstraction.Abstractor;
import it.rhai.settings.SettingsKeeper;

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
						.println("\t\t\t\tbuilt sequence n°: " + counter);
				counter++;
			}
		});
		thread.start();
		return null;
	}

}
