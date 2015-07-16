package it.rhai.test;

import it.rhai.reading.DataHandler;

public class TestingDataHandler<T> implements DataHandler<T> {

	private int counter = 1;	
	
	/* (non-Javadoc)
	 * @see it.rhai.test.DataHandler#handle(java.util.ArrayList)
	 */
	@Override
	public void handle(T data) {
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("\t\t\t\t\t\t\t\thandled data n°: "+counter);
				counter++;	
			}
		} );
		thread.start();
	}

}
