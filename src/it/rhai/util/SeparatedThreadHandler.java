package it.rhai.util;

public class SeparatedThreadHandler<T> extends ConsumerHandler<T> {

	private boolean waitingForData = true;
	boolean isRunning = false;
	private DataHandler<T> handlerOut;
	Thread handlerThread = new Thread(new Runnable() {

		@Override
		public synchronized void run() {
			while (isRunning) {
				while (dataSource.size() == 0 && isRunning) {
					if (!waitingForData) {
						isRunning = false;
					}
				}
				if (isRunning) {
					handlerOut.handle(dataSource.nextElement());
				}
			}
		}
	});

	public SeparatedThreadHandler(DataHandler<T> effectiveHandler) {
		this.handlerOut = effectiveHandler;
	}

	public synchronized void close() {
		this.waitingForData = false;
	}

	public boolean isRunning() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
		}
		return isRunning;
	}

	@Override
	protected void handleQueue() {
		isRunning = true;
		handlerThread.start();
	}
}
