package it.rhai.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

	private File file;

	public FileUtils(File file) {
		super();
		this.file = file;
	}

	public File replaceLabel(String replacer, String toBeReplaced)
			throws IOException {
		File output = new File(file.getName()+"-replaced." + getExtension());
		BufferedWriter writer = new BufferedWriter(new FileWriter(output));
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		while ((line = reader.readLine()) != null) {
			if (!line.contains(toBeReplaced)) {
				writer.write(line);
			} else {
				writer.write(newLine(line, replacer, toBeReplaced));
			}
		}
		writer.close();
		reader.close();
		return output;
	}

	public String getExtension() {
		String extension = "";

		int i = file.getName().lastIndexOf('.');
		if (i > 0) {
			extension = file.getName().substring(i + 1);
		}
		return extension;
	}

	private static String newLine(String line, String newString,
			String toBeReplaced) {
		return line.replace(toBeReplaced, newString);
	}
}
