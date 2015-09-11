package it.rhai.util.concurrent;

import it.rhai.util.DataHandler;

/**
 * This implementation of {@link DataHandler} interface acts like a producer
 * block. It accepts inputs and puts in a queue, ready to be consumed
 * 
 * @author simone
 *
 * @param <T>
 */
public class ProducerHandler<T> implements DataHandler<T> {

	private SynchronizedQueue<T> queue = new SynchronizedQueue<T>();

	/**
	 * Creates a new producer element
	 * 
	 * @param queue
	 *            : the queue to insert elements into
	 */
	public ProducerHandler(SynchronizedQueue<T> queue) {
		this.queue = queue;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.rhai.util.DataHandler#handle(java.lang.Object)
	 */
	public void handle(T toBeHandled) {
		queue.add(toBeHandled);
	}

}
