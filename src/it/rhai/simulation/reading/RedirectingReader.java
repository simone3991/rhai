package it.rhai.simulation.reading;

import it.rhai.settings.RHAISettings;
import it.rhai.util.DataHandler;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class represents an object able to read a stream of data and redirecting
 * to a {@link DataHandler} instance when a certain amount of data has been
 * reached. To further information of this length, see
 * {@link RHAISettings#getTAbstraction()}
 * 
 * @author simone
 *
 * @param <T>: the type of data to be received
 */
public class RedirectingReader<T> implements Reader<T> {

	private DataHandler<Collection<T>> handler;
	private int maxLength;
	private ArrayList<T> data;
	private int counter = 1;

	/**
	 * Creates a new instance of this class
	 * 
	 * @param handler
	 *            : the object this reader will redirect the data to
	 * @param redirectingLength
	 *            : the length of data that will be delivered to the handler
	 */
	public RedirectingReader(DataHandler<Collection<T>> handler) {
		this.handler = handler;
		data = new ArrayList<T>(maxLength);
	}

	public void setMaxLength(int redirectingLength) {
		this.maxLength = redirectingLength;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.rhai.reading.Reader#read(T)
	 */
	@Override
	public void read(T value) {
		data.add(value);
		if (data.size() == maxLength) {
			handler.handle(data);
			data.removeAll(data);
		}
		counter++;
	}
}
