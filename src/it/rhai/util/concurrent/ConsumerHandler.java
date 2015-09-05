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
public abstract class ConsumerHandler<T> implements
		DataHandler<SynchronizedQueue<T>> {

	private boolean handling = false;
	protected SynchronizedQueue<T> dataSource;

	@Override
	public void handle(SynchronizedQueue<T> toBeHandled) {
		if (!handling) {
			this.dataSource = toBeHandled;
			handleQueue();
			handling = true;
		}
	}

	protected abstract void handleQueue();

}