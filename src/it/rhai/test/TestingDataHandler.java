package it.rhai.test;

import it.rhai.reading.DataHandler;

import java.util.ArrayList;
import java.util.Collection;

public class TestingDataHandler<T, K> implements DataHandler<T, K> {

	private Collection<K> handled = null;
	
	/* (non-Javadoc)
	 * @see it.rhai.test.DataHandler#getHandled()
	 */
	@Override
	public Collection<K> getHandled() {
		return handled;
	}

	/* (non-Javadoc)
	 * @see it.rhai.test.DataHandler#handle(java.util.ArrayList)
	 */
	@Override
	public void handle(Collection<T> data) {
		handled = new ArrayList<K>();
		System.out.println("handled "+data.size()+" data");
	}

}
