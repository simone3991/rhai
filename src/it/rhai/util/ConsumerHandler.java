package it.rhai.util;

public abstract class ConsumerHandler<T> implements DataHandler<FIFOQueue<T>> {

	protected boolean handling = false;
	protected FIFOQueue<T> dataSource;

	@Override
	public void handle(FIFOQueue<T> toBeHandled) {
		if (!handling) {
			this.dataSource = toBeHandled;
			handleQueue();
			handling = true;
		}
	}

	protected abstract void handleQueue();

}