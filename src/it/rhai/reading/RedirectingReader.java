package it.rhai.reading;

import it.rhai.settings.RHAISettings;
import it.rhai.settings.SettingsKeeper;

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
	private int nextIndex = 0;
	private int counter = 1;

	/**
	 * Creates a new instance of this class
	 * 
	 * @param handler
	 *            : the object this reader will redirect the data to
	 */
	public RedirectingReader(DataHandler<Collection<T>> handler) {
		this.handler = handler;
		this.maxLength = SettingsKeeper.getSettings().getTAbstraction();
		data = new ArrayList<T>(maxLength);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.rhai.reading.Reader#read(T)
	 */
	@Override
	public void read(T value) {
		System.out.println("read data n°: " + counter);
		data.add(nextIndex, value);
		nextIndex++;
		counter++;
		if (nextIndex == maxLength) {
			handler.handle(data);
			nextIndex = 0;
		}
	}
}
