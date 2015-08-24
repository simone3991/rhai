package it.rhai.simulation.reading;

import it.rhai.util.DataHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Handler;

/**
 * This class represents an object able to read a stream of data and redirecting
 * to a {@link DataHandler} instance when a certain amount of data has been
 * reached
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
	 * Creates a new instance of this class. The amount of data to be reached
	 * before delivering them to the {@link Handler} is now 1. To set a
	 * different value, just call {@link #setMaxLength(int)}
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
