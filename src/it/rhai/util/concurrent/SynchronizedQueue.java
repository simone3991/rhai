package it.rhai.util.concurrent;

import java.util.LinkedList;

/**
 * This class provides a simple object to perform producer-consumer operations.
 * This queue allows to add produced elements and to get the next element to be
 * consumed. Hence, this buffer implements a first in first out logic. To avoid
 * critical races during buffer operations (add or get), theese methods are
 * synchronized
 * 
 * @author simone
 *
 * @param <T>
 */
public class SynchronizedQueue<T> {

	private LinkedList<T> buffer = new LinkedList<T>();

	/**
	 * Add an element on this buffer
	 * 
	 * @param element
	 *            : the element to be added
	 * @return: a logic value, as a flag to indicate whether the addition took
	 *          place or not
	 */
	public synchronized boolean addElement(T element) {
		boolean offer = buffer.offer(element);
		synchronized (this) {
			if (buffer.size() == 1) {
				super.notifyAll();
			}
		}
		return offer;
	}

	/**
	 * Returns the next element to be used in this buffer, that meaning the
	 * oldest one
	 * 
	 * @return: the next element of this buffer to be used
	 */
	public synchronized T nextElement() {
		return buffer.pollFirst();
	}

	public int size() {
		return buffer.size();
	}
}
