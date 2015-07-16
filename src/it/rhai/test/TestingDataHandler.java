package it.rhai.test;

import it.rhai.reading.DataHandler;

import java.util.Collection;

public class TestingDataHandler<T> implements DataHandler<T> {

	/* (non-Javadoc)
	 * @see it.rhai.test.DataHandler#handle(java.util.ArrayList)
	 */
	@Override
	public void handle(Collection<T> data) {
		System.out.println("handled "+data.size()+" data");
	}

}
