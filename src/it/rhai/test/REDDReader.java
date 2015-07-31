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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

public class REDDReader {

	private static final int THREESHOLD = 10;
	private static ArrayList<ArrayList<PowerMeasure>> results = new ArrayList<ArrayList<PowerMeasure>>();
	private static int currentResult = -1;

	public static void main(String[] args) throws Exception {
		File file = new File(
				"/home/simone/Scrivania/low_freq/house_1/channel_10.dat");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		StringTokenizer tokenizer;
		boolean writing = false;
		while ((line = reader.readLine()) != null) {
			tokenizer = new StringTokenizer(line);
			Long timestamp = Long.parseLong(tokenizer.nextToken().trim());
			Double measure = Double.parseDouble(tokenizer.nextToken().trim());
			if (measure >= THREESHOLD) {
				if (writing) {
					results.get(currentResult).add(
							new PowerMeasure(toCalendar(timestamp), measure));
				} else {
					currentResult++;
					results.add(currentResult, new ArrayList<PowerMeasure>());
					results.get(currentResult).add(
							new PowerMeasure(toCalendar(timestamp), measure));
					writing = true;
				}
			} else {
				if (writing) {
					writing = false;
				}
			}
		}
		reader.close();
		for (int i = 0; i < results.size(); i++) {
			printResult(i);
		}
	}

	private static void printResult(int i) throws IOException {
		prepareList();
		File file = new File("/home/simone/Scrivania/tmp/result_" + i);
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		for (PowerMeasure measure : results.get(i)) {
			writer.write(measure.toString() + "\n");
		}
		writer.close();
	}

	private static void prepareList() {
		Collections.sort(results, new Comparator<ArrayList<PowerMeasure>>() {

			@Override
			public int compare(ArrayList<PowerMeasure> o1,
					ArrayList<PowerMeasure> o2) {
				return -(Integer.valueOf(o1.size())).compareTo(Integer
						.valueOf(o2.size()));
			}
		});
	}

	private static Calendar toCalendar(Long timestamp) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(toDate(timestamp));
		return calendar;
	}

	private static Date toDate(Long timestamp) {
		return new Date(timestamp * 1000L);
	}
}
