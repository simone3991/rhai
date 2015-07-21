package it.rhai.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;

/**
 * A collection of {@link DataHandler} able to display outputs
 * 
 * @author simone
 *
 */
public class Outputs {

	private static final HashMap<String, DataHandler<String>> outputs = new HashMap<String, DataHandler<String>>();

	static {
		outputs.put("stdout", new DataHandler<String>() {

			@Override
			public void handle(String toBeHandled) {
				System.out.println(toBeHandled);
			}
		});
		try {
			outputs.put("file", new DataHandler<String>() {

				private PrintStream stream = new PrintStream(new File(
						"data/output.txt"));

				@Override
				public void handle(String toBeHandled) {
					stream.println(toBeHandled);
				}
			});
		} catch (FileNotFoundException e) {
		}
		outputs.put("gui", null);
	}

	private Outputs() {

	}

	public static DataHandler<String> getOutput(String output) {
		return outputs.get(output);
	}
}
