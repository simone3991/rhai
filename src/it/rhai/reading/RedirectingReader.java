package it.rhai.reading;

import it.rhai.settings.RHAISettings;
import it.rhai.settings.SettingsKeeper;

import java.util.ArrayList;

/**
 * This class represents an object able to read a stream of data and redirecting
 * to a {@link DataHandler} instance when a certain length has been reached. To
 * further information of this length, see
 * {@link RHAISettings#getTAbstraction()}
 * 
 * @author simone
 *
 * @param <T>: the type od data to be received
 */
public class RedirectingReader<T> implements Reader<T> {

	private DataHandler<T, Object> handler;
	private int maxLength;
	private ArrayList<T> data;
	private int nextIndex = 0;

	/**
	 * Creates a new reader
	 * 
	 * @param handler
	 *            : the object this reader will redirect the data to
	 */
	public RedirectingReader(DataHandler<T, Object> handler) {
		this.handler = handler;
		this.maxLength = SettingsKeeper.getSettings().getTAbstraction();
		data = new ArrayList<T>(maxLength);
	}

	/* (non-Javadoc)
	 * @see it.rhai.reading.Reader#read(T)
	 */
	@Override
	public void read(T value) {
		data.add(nextIndex, value);
		nextIndex++;
		if (nextIndex == maxLength) {
			handler.handle(data);
			nextIndex = 0;
		}
	}
}
