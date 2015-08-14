package it.rhai.test.gui;

import static org.junit.Assert.assertEquals;
import it.rhai.gui.Application;
import it.rhai.gui.ApplicationElement;

import org.junit.Test;

public class ApplicationTest {

	private static int current = 0;
	private static Application application;

	static {
		ApplicationElement el1 = new ApplicationElement() {

			@Override
			public void turnOn() {
				current = 1;
				System.out.println("turning on 1");
			}

			@Override
			public void turnOff() {
			}

			@Override
			public void setApplication(Application application) {
			}
		};

		ApplicationElement el2 = new ApplicationElement() {

			@Override
			public void turnOn() {
				current = 2;
				System.out.println("turning on 2");
			}

			@Override
			public void turnOff() {
			}

			@Override
			public void setApplication(Application application) {
			}
		};
		application = new Application(el1, el2);
	}

	@Test
	public void testStart() {
		application.start();
		assertEquals(1, current);
	}

	@Test
	public void testNext() {
		application.start();
		application.next();
		assertEquals(2, current);
	}

	@Test
	public void testRestart() {
		application.start();
		application.next();
		application.restart();
		assertEquals(1, current);
	}

	@Test
	public void testSetGetParam() {
		Object obj = new Object();
		application.setParam(obj.toString(), obj);
		assertEquals(obj.hashCode(), application.getParam(obj.toString())
				.hashCode());
	}

}
