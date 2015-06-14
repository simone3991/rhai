package it.rhai.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class XMLAdjuster {

	private static final String REPLACEMENT_LABEL = "$TMP";

	public static void adjust(String newString) throws IOException {
		File input = new File("template.xml");
		File output = new File("sequencer.xml");
		BufferedWriter writer = new BufferedWriter(new FileWriter(output));
		BufferedReader reader = new BufferedReader(new FileReader(input));
		String line;
		while ((line = reader.readLine()) != null) {
			if (!line.contains(REPLACEMENT_LABEL)) {
				writer.write(line);
			} else {
				writer.write(newLine(line, newString));
			}
		}
		writer.close();
		reader.close();
	}

	private static String newLine(String line, String newString) {
		return line.replace(REPLACEMENT_LABEL, newString);
	}
}
