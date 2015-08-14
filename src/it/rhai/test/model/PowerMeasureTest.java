package it.rhai.test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import it.rhai.model.PowerMeasure;

import java.util.Calendar;

import org.junit.Test;

public class PowerMeasureTest {

	@Test
	public void testGetDate() {
		Calendar date = Calendar.getInstance();
		Double value = 10.0;
		PowerMeasure measure = new PowerMeasure(date, value);
		assertEquals(date, measure.getDate());
	}

	@Test
	public void testGetValue() {
		Calendar date = Calendar.getInstance();
		Double value = 10.0;
		PowerMeasure measure = new PowerMeasure(date, value);
		assertEquals(value, measure.getValue());
	}

	@Test
	public void testParsePowerMeasure() {
		Calendar date = Calendar.getInstance();
		Double value = 10.0;
		PowerMeasure measure = new PowerMeasure(date, value);
		assertEquals(measure,
				PowerMeasure.parsePowerMeasure(measure.toString()));
	}

	@Test
	public void testCompareTo() throws Exception {
		Calendar date = Calendar.getInstance();
		Double value = 10.0;
		PowerMeasure measure = new PowerMeasure(date, value);
		Thread.sleep(100);
		Calendar date2 = Calendar.getInstance();
		PowerMeasure measure2 = new PowerMeasure(date, value);
		assertEquals(date.compareTo(date2), measure.compareTo(measure2));
	}

	@Test
	public void testEqualsObject() {
		Calendar date = Calendar.getInstance();
		Double value = 10.0;
		PowerMeasure measure = new PowerMeasure(date, value);
		PowerMeasure measure2 = new PowerMeasure(date, value*2);
		assertFalse(measure.equals(measure2));
	}

}
