package it.rhai.test.util;

import it.rhai.util.DataHandler;
import it.rhai.util.ProducerHandler;
import it.rhai.util.SeparatedThreadHandler;

import org.junit.Test;

public class ProducerConsumerTest {

	SeparatedThreadHandler<Integer> consumer = new SeparatedThreadHandler<Integer>(
			new DataHandler<Integer>() {

				@Override
				public void handle(Integer toBeHandled) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("handling " + toBeHandled);
				}
			});
	ProducerHandler<Integer> producer = new ProducerHandler<Integer>(consumer);
	int[] array = { 1, 2, 3, 4, 5 };

	@Test
	public void test() {
		for (int i : array) {
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			producer.handle(i);
			System.out.println("producing " + i);
		}
		consumer.close();
		while (consumer.isRunning())
			;
		System.out.println("finished work");
	}

}
