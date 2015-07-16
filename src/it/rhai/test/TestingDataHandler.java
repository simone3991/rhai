package it.rhai.test;

import it.rhai.reading.DataHandler;

import java.util.Collection;

public class TestingDataHandler<T> implements DataHandler<T> {

	private int counter = 1;	
	
	/* (non-Javadoc)
	 * @see it.rhai.test.DataHandler#handle(java.util.ArrayList)
	 */
	@Override
	public void handle(T data) {
		System.out.println("handled data n°: "+counter);
		counter++;
	}

}
