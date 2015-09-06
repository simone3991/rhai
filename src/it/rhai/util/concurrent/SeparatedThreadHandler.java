package it.rhai.util.concurrent;

import it.rhai.util.DataHandler;

/**
 * This subclass of {@link ConsumerHandler} provides a multi-thread behavior to
 * accomplish consumer operations. To use this class, just pass it as a
 * parameter to the {@link ProducerHandler} object you are using. This handler
 * will wait for input to arrive till the {@link #close()} method is not called.
 * After that, it will handle pending input but reject new ones. Too wait for
 * this thread to finish, use a busy wait with the boolean {@link #isRunning()}
 * method
 * 
 * @author simone
 *
 * @param <T>
 */
public class SeparatedThreadHandler<T> extends ConsumerHandler<T> {

	private boolean waitingForData = true;
	boolean isRunning = false;
	private DataHandler<T> handlerOut;
	private Thread handlerThread = new Thread(new Runnable() {

		@Override
		public void run() {
			synchronized (dataSource) {
				while (true) {
					if (dataSource.awaitingSize() != 0) {
						handlerOut.handle(dataSource.next());
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

	/**
	 * Creates a new instance of this class
	 * 
	 * @param effectiveHandler
	 *            : the {@link DataHandler} that implements the consumer
	 *            operations
	 */
	public SeparatedThreadHandler(DataHandler<T> effectiveHandler) {
		this.handlerOut = effectiveHandler;
	}

	/**
	 * Closes this object, that meaning it will reject new inputs after this
	 * method been called
	 */
	public void close() {
		this.waitingForData = false;
	}

	/**
	 * Tells you whether or not this handler has completed its task. Before
	 * calling the {@link #close()} method this will always result in false (not
	 * viceversa)
	 * 
	 * @return
	 */
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see it.rhai.util.concurrent.ConsumerHandler#handleQueue()
	 */
	protected void handleQueue() {
		isRunning = true;
		handlerThread.start();
	}
}
