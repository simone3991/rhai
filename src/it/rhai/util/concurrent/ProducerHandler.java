package it.rhai.util.concurrent;

import it.rhai.util.DataHandler;

public class ProducerHandler<T> implements DataHandler<T> {

	private SynchronizedQueue<T> queue = new SynchronizedQueue<T>();

	public ProducerHandler(ConsumerHandler<T> handler) {
		handler.handle(queue);
	}

	@Override
	public void handle(T toBeHandled) {
		queue.addElement(toBeHandled);
	}

}
