package it.rhai.test.simulation.reading;

import it.rhai.simulation.reading.RedirectingReader;
import it.rhai.util.DataHandler;

import java.util.Collection;

import static org.junit.Assert.*;

import org.junit.Test;

public class RedirectingReaderTest {

	private int collectionsReceived = 0;

	@Test
	public void testBehavior() {
		RedirectingReader<Integer> reader = new RedirectingReader<Integer>(
				new DataHandler<Collection<Integer>>() {

					@Override
					public void handle(Collection<Integer> toBeHandled) {
						collectionsReceived ++;
					}
				});
		reader.setMaxLength(2);
		for (Integer i = 0; i < 10; i++) {
			reader.read(i);
		}
		assertEquals(5, collectionsReceived);
	}
}
