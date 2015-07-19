package it.rhai.util;

/**
 * This interface represents a generic object able to handle inputs
 * 
 * @author simone
 *
 * @param <T>: the type of inputs
 */
public interface DataHandler<T> {

	/**
	 * Handles the input
	 * 
	 * @param data
	 *            : the input dasta to be handled
	 */
	public void handle(T data);

}