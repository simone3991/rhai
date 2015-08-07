package it.rhai.settings;

import it.rhai.util.DataHandler;

import java.io.PrintStream;

/**
 * A generic container of useful informations
 * 
 * @author simone
 *
 */
public interface RHAIUtilSettings {

	/**
	 * Returns a stream where log any debug message
	 * 
	 * @return: a {@link PrintStream} where messages should be printed
	 */
	public DataHandler<String> getDebugLogger();
}