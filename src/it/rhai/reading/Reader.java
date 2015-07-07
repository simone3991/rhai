package it.rhai.reading;

public interface Reader<T> {

	/**
	 * Reads a new data value
	 * 
	 * @param value
	 *            : the received value
	 */
	public void read(T value);

}