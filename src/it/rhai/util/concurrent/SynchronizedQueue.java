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
	private boolean producing = true;

	/**
	 * Add an element on this buffer
	 * 
	 * @param element
	 *            : the element to be added
	 * @return: a logic value, as a flag to indicate whether the addition took
	 *          place or not
	 */
	public synchronized void add(T element) {
		if (producing) {
			buffer.offer(element);
			if (buffer.size() == 1) {
				super.notifyAll();
			}
		}
	}

	/**
	 * Returns the next element to be used in this buffer, that meaning the
	 * oldest one. This method removes the element from the queue
	 * 
	 * @return: the next element of this buffer to be used
	 */
	public synchronized T next() {
		waitForElements();
		return buffer.pollFirst();
	}

	/**
	 * Returns the next element to be used in this buffer, that meaning the
	 * oldest one. This method does not remove the element
	 * 
	 * @return: the next element of this buffer to be used
	 */
	public synchronized T seeNext() {
		waitForElements();
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

	/**
	 * Returns a flag to indicate whether or not reading this queue is worth it:
	 * it is not pointless either when some object is writing into it either
	 * when it is not empty. If neither of theese condition do hold, reading the
	 * queue will be meanless
	 * 
	 * @return: a flag to indicate whether or not reading this queue is useful
	 */
	public boolean isActive() {
		return producing || !buffer.isEmpty();
	}

	/**
	 * Closes this queue: since this method call, calling {@link #add(Object)}
	 * will be ineffective
	 */
	public void close() {
		producing = false;
	}

	private void waitForElements() {
		while (buffer.isEmpty()) {
			try {
				super.wait();
			} catch (InterruptedException e) {
			}
		}
	}
}
