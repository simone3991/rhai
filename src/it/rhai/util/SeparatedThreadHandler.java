package it.rhai.util;

import java.io.Closeable;

public class SeparatedThreadHandler<T> implements DataHandler<T>, Closeable {

	private boolean threadStarted = false;
	private boolean closed = false;
	private boolean running = false;
	private FIFOQueue<T> queue = new FIFOQueue<T>();
	private DataHandler<T> handlerOut;
	private Thread handlerThread;

	public SeparatedThreadHandler(String name, DataHandler<T> effectiveHandler) {
		this.handlerOut = effectiveHandler;
		this.handlerThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (queue.size() == 0) {
					if (closed) {
						handlerThread.interrupt();
						running = false;
					}
				}
				handlerOut.handle(queue.nextElement());
			}
		}, name);
	}

	@Override
	public void handle(T toBeHandled) {
		queue.addElement(toBeHandled);
		if (!threadStarted) {
			threadStarted = true;
			running = true;
			handlerThread.start();
		}
	}

	@Override
	public void close() {
		closed = true;
	}

	public boolean isRunning() {
		return running;
	}
}
