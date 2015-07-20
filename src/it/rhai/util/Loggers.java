package it.rhai.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;

public class Loggers {

	public static HashMap<String, PrintStream> loggers = new HashMap<String, PrintStream>();

	static {
		loggers.put("stdout", System.out);
		loggers.put("stderr", System.err);
		try {
			loggers.put("file-logger", new PrintStream(new File("data/debug.txt")));
		} catch (FileNotFoundException e) {
		}
	}

	private Loggers() {

	}
}
