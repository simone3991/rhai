package it.rhai.util.concurrent;

import it.rhai.util.DataHandler;

public class SeparatedThreadHandler<T> extends ConsumerHandler<T> {

	private boolean waitingForData = true;
	boolean isRunning = false;
	private DataHandler<T> handlerOut;
	Thread handlerThread = new Thread(new Runnable() {

		@Override
		public void run() {
			synchronized (dataSource) {
				while (true) {
					if (dataSource.size() != 0) {
						handlerOut.handle(dataSource.nextElement());
					} else {
						if (waitingForData) {
							try {
								dataSource.wait();
								continue;
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						break;
					}
				}
			}
			isRunning = false;
		}
	});

	public SeparatedThreadHandler(DataHandler<T> effectiveHandler) {
		this.handlerOut = effectiveHandler;
	}

	public void close() {
		this.waitingForData = false;
	}

	public boolean isRunning() {
		return isRunning;
	}

	@Override
	protected void handleQueue() {
		isRunning = true;
		handlerThread.start();
	}
}
