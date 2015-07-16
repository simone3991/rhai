package it.rhai.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

public class PowerMeasure {

	private Calendar date;
	private Double value;

	public PowerMeasure(Calendar date, Double value) {
		super();
		this.date = date;
		this.value = value;
	}

	public Calendar getDate() {
		return date;
	}

	public Double getValue() {
		return value;
	}

	public static PowerMeasure parsePowerMeasure(String line) {
		StringTokenizer fullTokenizer = new StringTokenizer(line);
		String dateString = fullTokenizer.nextToken();
		StringTokenizer dateTokenizer = new StringTokenizer(dateString, "/");
		int day = Integer.parseInt(dateTokenizer.nextToken());
		int month = Integer.parseInt(dateTokenizer.nextToken());
		int year = Integer.parseInt(dateTokenizer.nextToken());
		String hourString = fullTokenizer.nextToken();
		StringTokenizer hourTokenizer = new StringTokenizer(hourString, ":");
		int hour = Integer.parseInt(hourTokenizer.nextToken());
		int min = Integer.parseInt(hourTokenizer.nextToken());
		int secs = Integer.parseInt(hourTokenizer.nextToken());
		double value = Double.parseDouble(fullTokenizer.nextToken());
		Calendar calendar = new GregorianCalendar(year, month-1, day, hour, min,
				secs);
		PowerMeasure measure = new PowerMeasure(calendar, value);
		return measure;
	}

	@Override
	public String toString() {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return format.format(date.getTime())+"\t"+value;
	}
}
