package it.rhai.util;

import java.util.ArrayList;

/**
 * This class provides a simple object to perform producer-consumer operations.
 * This buffer allows to be add produced element and to get the next to be
 * consumed element consumed. Hence, this buffer implements a first in first out
 * logic. To avoid critical races during buffer operations (add or get), theese
 * methods are synchronized
 * 
 * @author simone
 *
 * @param <T>
 */
public class DoubleBuffer<T> {

	private ArrayList<T> buffer = new ArrayList<T>();

	/**
	 * Add an element on this buffer
	 * 
	 * @param element
	 *            : the element to be added
	 * @return: a logic value, as a flag to indicate whether the addition took
	 *          place or not
	 */
	public synchronized boolean addElement(T element) {
		return buffer.add(element);
	}

	/**
	 * Returns the next element to be used in this buffer, that meaning the
	 * oldest one
	 * 
	 * @return: the next element of this buffer to be used
	 */
	public synchronized T nextElement() {
		T element = null;
		if (this.size() > 0) {
			element = buffer.get(0);
			this.shift();
		}
		return element;
	}

	public int size() {
		return buffer.size();
	}

	private void shift() {
		ArrayList<T> shifted = new ArrayList<T>();
		for (int i = 1; i < this.size(); i++) {
			shifted.add(buffer.get(i));
		}
		buffer = shifted;
	}
}
