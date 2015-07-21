package it.rhai.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;

/**
 * A collection of {@link DataHandler} able to log a message
 * 
 * @author simone
 *
 */
public class Loggers {

	private static final HashMap<String, DataHandler<String>> loggers = new HashMap<String, DataHandler<String>>();

	static {
		loggers.put("stdout", new DataHandler<String>() {

			@Override
			public void handle(String toBeHandled) {
				System.out.println(toBeHandled);
			}
		});
		loggers.put("stderr", new DataHandler<String>() {

			@Override
			public void handle(String toBeHandled) {
				System.err.println(toBeHandled);
			}
		});
		try {
			loggers.put("file-logger", new DataHandler<String>() {

				private PrintStream stream = new PrintStream(new File(
						"data/debug.txt"));

				@Override
				public void handle(String toBeHandled) {
					stream.println(toBeHandled);
				}
			});
		} catch (FileNotFoundException e) {
		}
	}

	private Loggers() {

	}

	public static DataHandler<String> getLogger(String logger) {
		return loggers.get(logger);
	}
}
