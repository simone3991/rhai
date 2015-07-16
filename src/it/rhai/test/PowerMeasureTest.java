package it.rhai.test;

import static org.junit.Assert.*;
import it.rhai.model.PowerMeasure;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

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
}
