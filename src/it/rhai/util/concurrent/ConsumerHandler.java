package it.rhai.util.concurrent;

import it.rhai.util.DataHandler;

public abstract class ConsumerHandler<T> implements DataHandler<SynchronizedQueue<T>> {

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