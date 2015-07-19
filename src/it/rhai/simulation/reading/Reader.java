package it.rhai.simulation.reading;

/**
 * A generic reader
 * 
 * @author simone
 *
 * @param <T>
 */
public interface Reader<T> {

	/**
	 * Reads a new data value
	 * 
	 * @param value
	 *            : the received value
	 */
	public void read(T value);

}