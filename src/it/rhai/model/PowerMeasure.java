package it.rhai.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

/**
 * This class represents a single element of power measure, thus identified by
 * its value and its date. It implements {@link Comparable} to allow natural
 * sorting: two measures are compared according to their date
 * 
 * @author simone
 *
 */
public class PowerMeasure implements Comparable<PowerMeasure> {

	private Calendar date;
	private Double value;

	/**
	 * Creates a new instance of this class
	 * 
	 * @param date
	 *            : the {@link Calendar} instance representing this measure's
	 *            date
	 * @param value
	 *            : the value of the measure [W]
	 */
	public PowerMeasure(Calendar date, Double value) {
		super();
		this.date = date;
		this.value = value;
	}

	/**
	 * Returns the date of this measure
	 * 
	 * @return
	 */
	public Calendar getDate() {
		return date;
	}

	/**
	 * Returns the watts of this measure
	 * 
	 * @return
	 */
	public Double getValue() {
		return value;
	}

	/**
	 * Parses a string into a measure. To properly work, the input string must
	 * be like "gg/mm/yyyy value"
	 * 
	 * @param line
	 *            : the string to be parsed
	 * @return: the represented measure
	 */
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
		Calendar calendar = new GregorianCalendar(year, month - 1, day, hour,
				min, secs);
		PowerMeasure measure = new PowerMeasure(calendar, value);
		return measure;
	}

	/**
	 * Returns an estimation of the average sampling time between a list of
	 * measures, in seconds
	 * 
	 * @param measures
	 *            : a collection of measures
	 * @return: the estimated sampling time between a measure and another
	 */
	public static int computeSamplingTime(List<PowerMeasure> measures) {
		return (int) ((measures.get(measures.size() - 1).getDate()
				.getTimeInMillis() - measures.get(0).getDate()
				.getTimeInMillis()) / ((measures.size() - 1))) / 1000;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return format.format(date.getTime()) + "\t" + value;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(PowerMeasure other) {
		return date.compareTo(other.date);
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		PowerMeasure otherMeasure;
		try {
			otherMeasure = (PowerMeasure) other;
		} catch (ClassCastException exception) {
			return false;
		}
		return (date.getTimeInMillis() == otherMeasure.date.getTimeInMillis())
				&& (value == otherMeasure.value);
	}
}
