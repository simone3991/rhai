package it.rhai.model;

import java.util.Calendar;

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
		return null;
	}

}
