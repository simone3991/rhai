package it.rhai.util;

public class ProducerHandler<T> implements DataHandler<T> {

	private FIFOQueue<T> queue = new FIFOQueue<T>();

	public ProducerHandler(ConsumerHandler<T> handler) {
		handler.handle(queue);
	}

	@Override
	public void handle(T toBeHandled) {
		queue.addElement(toBeHandled);
	}

}
