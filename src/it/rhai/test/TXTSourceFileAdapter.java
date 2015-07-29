package it.rhai.test;

import it.rhai.model.PowerMeasure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

public class TXTSourceFileAdapter {

	private static ArrayList<String> strings = new ArrayList<String>();
	private static ArrayList<PowerMeasure> measures = new ArrayList<PowerMeasure>();
	private static Calendar startingDate = Calendar.getInstance();

	private static void adapt(File file) throws IOException {
		readFile(file);
		convertStringsToMeasures();
		overWrite(file);
	}

	private static void overWrite(File file) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
		for (PowerMeasure powerMeasure : measures) {
			writer.write(powerMeasure.toString() + "\n");
		}
		writer.close();
	}

	private static void convertStringsToMeasures() {
		double watts = 0;
		for (String string : strings) {
			StringTokenizer tokenizer = new StringTokenizer(string);
			watts = Double.parseDouble(tokenizer.nextToken());
			Calendar thisDate = getNewDate(tokenizer);
			measures.add(new PowerMeasure(thisDate, watts));
		}
	}

	private static Calendar getNewDate(StringTokenizer tokenizer) {
		int offset = 0;
		offset = Integer.parseInt(tokenizer.nextToken());
		Calendar thisDate = new GregorianCalendar(
				startingDate.get(Calendar.YEAR),
				startingDate.get(Calendar.MONTH),
				startingDate.get(Calendar.DAY_OF_MONTH),
				startingDate.get(Calendar.HOUR),
				startingDate.get(Calendar.MINUTE),
				startingDate.get(Calendar.SECOND) + offset);
		return thisDate;
	}

	private static void readFile(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = reader.readLine();
		while ((line = reader.readLine()) != null) {
			strings.add(line);
		}
		reader.close();
	}

	public static void main(String[] args) throws IOException {
		adapt(new File(
				"/home/simone/Scaricati/carichi_elettrici/LavatriceRiscaldamento.txt"));
	}
}
