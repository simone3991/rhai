package it.rhai.test.util;

import it.rhai.util.concurrent.SynchronizedQueue;
import static org.junit.Assert.*;

import org.junit.Test;

public class FIFOQueueTest {

	SynchronizedQueue<Object> buffer = new SynchronizedQueue<Object>();
	Object ob1 = new Object();
	Object ob2 = new Object();
	Object ob3 = new Object();

	@Test
	public void testAdd() {
		buffer.add(ob1);
		buffer.add(ob2);
		assertEquals(2, buffer.awaitingSize());
	}

	@Test
	public void testGet() {
		buffer.add(ob1);
		buffer.add(ob2);
		buffer.add(ob3);
		assertEquals(ob1, buffer.next());
		assertEquals(ob2, buffer.next());
		assertEquals(1, buffer.awaitingSize());
		assertEquals(ob3, buffer.next());
	}
}
