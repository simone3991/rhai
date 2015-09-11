package it.rhai.test.util;

import org.junit.Test;

import static org.junit.Assert.*;
import it.rhai.util.DataHandler;
import it.rhai.util.concurrent.ConsumerHandler;
import it.rhai.util.concurrent.ProducerHandler;
import it.rhai.util.concurrent.SynchronizedQueue;

public class ProducerConsumerTest {

	private int pending;

	@Test
	public void test() throws Exception {
		String[] elements = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		pending = elements.length;
		SynchronizedQueue<String> queue = new SynchronizedQueue<String>();
		ProducerHandler<String> producer = new ProducerHandler<String>(queue);
		ConsumerHandler<String> consumer = new ConsumerHandler<String>(queue,
				new DataHandler<String>() {

					@Override
					public void handle(String toBeHandled) {
						try {
							Thread.sleep(5);
						} catch (InterruptedException e) {
						}
						pending--;
					}
				});
		consumer.start();
		for (String string : elements) {
			Thread.sleep(2);
			producer.handle(string);
		}
		queue.close();
		consumer.join();
		assertEquals(0, pending);
	}

}
