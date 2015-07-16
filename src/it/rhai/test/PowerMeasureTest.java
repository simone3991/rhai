package it.rhai.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.rhai.model.PowerMeasure;

import java.util.GregorianCalendar;

import org.junit.Test;

public class PowerMeasureTest {

	@Test
	public void testPowerMeasure() {
	}

	@Test
	public void testGetDate() {
	}

	@Test
	public void testGetValue() {
	}

	@Test
	public void testParsePowerMeasure() {
		String line = "26/5/2015 13:25:51	10.610579";
		PowerMeasure measure = PowerMeasure.parsePowerMeasure(line);
		assertEquals(10.610579, measure.getValue(), 0);
		assertTrue(measure.getDate().before(new GregorianCalendar(2015, 5, 27)));
	}

	@Test
	public void testToString() throws Exception {
		String line = "26/05/2015 13:25:51	10.610579";
		PowerMeasure measure = PowerMeasure.parsePowerMeasure(line);
		assertEquals(line, measure.toString());
	}
}
