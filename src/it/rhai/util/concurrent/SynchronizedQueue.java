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
	public synchronized boolean add(T element) {
		boolean offer = buffer.offer(element);
		if (buffer.size() == 1) {
			super.notifyAll();
		}
		return offer;
	}

	/**
	 * Returns the next element to be used in this buffer, that meaning the
	 * oldest one. This method removes the element from the queue
	 * 
	 * @return: the next element of this buffer to be used
	 */
	public synchronized T next() {
		return buffer.pollFirst();
	}

	/**
	 * Returns the next element to be used in this buffer, that meaning the
	 * oldest one. This method does not remove the element
	 * 
	 * @return: the next element of this buffer to be used
	 */
	public T seeNext() {
		return buffer.peekFirst();
	}

	/**
	 * Returns the number of pending elements currently into this queue
	 * 
	 * @return: the number of elements in this queue
	 */
	public int awaitingSize() {
		return buffer.size();
	}
}
