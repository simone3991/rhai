package it.rhai.test;

import it.rhai.settings.SettingsKeeper;
import it.rhai.util.DataHandler;

public class TestingDataHandler<T> implements DataHandler<T> {

	private int counter = 1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.rhai.test.DataHandler#handle(java.util.ArrayList)
	 */
	@Override
	public void handle(T data) {
		System.out.println("\t\t\t\tbuilt sequence: " + data);
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				SettingsKeeper.getSettings().getDebugLogger()
						.println("\t\t\t\t\t\t\t\thandled data n°: " + counter);
				counter++;
			}
		});
		thread.start();
	}

}
