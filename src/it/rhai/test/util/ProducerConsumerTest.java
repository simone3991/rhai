package it.rhai.test.util;

import it.rhai.util.DataHandler;
import it.rhai.util.concurrent.ProducerHandler;
import it.rhai.util.concurrent.SeparatedThreadHandler;

import org.junit.Test;

public class ProducerConsumerTest {

	SeparatedThreadHandler<Integer> consumer = new SeparatedThreadHandler<Integer>(
			new DataHandler<Integer>() {

				@Override
				public void handle(Integer toBeHandled) {
					System.out.println("handled " + toBeHandled);
				}
			});
	ProducerHandler<Integer> producer = new ProducerHandler<Integer>(consumer);
	int[] array = { 1, 2, 3, 4, 5 };

	@Test
	public void test() {
		for (int i : array) {
			producer.handle(i);
			System.out.println("produced " + i);
		}
		consumer.close();
		while (consumer.isRunning())
			;
		System.out.println("finished work");
	}

}
