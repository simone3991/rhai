package it.rhai.util.concurrent;

import it.rhai.util.DataHandler;

/**
 * This implementation of {@link DataHandler} interface acts like a consumer
 * block, thus handling a queue of elements. Note that calling more than once
 * the {@link #handle(SynchronizedQueue)} method will be meanless
 * 
 * @author simone
 *
 * @param <T>
 */
public class ConsumerHandler<T> extends Thread {

	private SynchronizedQueue<T> queue;
	private DataHandler<T> handler;

	/**
	 * Creates a new consumer element
	 * 
	 * @param queue
	 *            : the {@link SynchronizedQueue} where elements will be taken
	 * @param realHandler
	 *            : the handler to consume elements
	 */
	public ConsumerHandler(SynchronizedQueue<T> queue,
			DataHandler<T> realHandler) {
		this.queue = queue;
		this.handler = realHandler;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		while (queue.isActive()) {
			handler.handle(queue.next());
		}
	}
}