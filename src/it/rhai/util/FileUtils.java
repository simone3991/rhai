package it.rhai.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class collects different algorithms to work on {@link File} instances
 * 
 * @author simone
 *
 */
public class FileUtils {

	private FileUtils() {
	}

	/**
	 * Opens the given file, searching for a specific label: whenever it finds
	 * it, it replaces that label with another one.
	 * 
	 * @param replacer
	 *            : the new label to write
	 * @param toBeReplaced
	 *            : the label to be replaced with the new one
	 * @param file
	 *            : the original file
	 * @return: a new file equal to the given one except for that label
	 * @throws IOException
	 *             : if file can't be opened or something happens writing the
	 *             new output file
	 */
	public static File replaceLabel(String replacer, String toBeReplaced,
			File file) throws IOException {
		File output = new File(file.getName() + "-replaced."
				+ getExtension(file));
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

	/**
	 * Returns the extension of a file
	 * 
	 * @param file
	 *            : the file whose extension must be defined
	 * @return: the extension of the given file
	 */
	public static String getExtension(File file) {
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
