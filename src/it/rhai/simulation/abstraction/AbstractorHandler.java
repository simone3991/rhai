package it.rhai.simulation.abstraction;

import it.distanciable.sequences.Sequence;
import it.rhai.settings.SettingsKeeper;
import it.rhai.util.DataHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

/**
 * This implementation of {@link DataHandler} interface represents an
 * intermedium block of multiple data abstraction. This is performed by a
 * concrete implementation of {@link Abstractor} interface. The extracted
 * sequence is then redirected to an effective data handler. In other words,
 * this class converts a generic collection of data elements into a
 * {@link Sequence} of different type objects, and eventually let it be handled
 * by an other handler
 * 
 * @author simone
 *
 * @param <T>: the type of data to be handled
 * @param <K>: the type of the sequence to be extracted, and, therefore, the
 *        type of the data to be handled after this block
 */
public class AbstractorHandler<T, K> implements DataHandler<Collection<T>> {

	private static final String TMP_FILENAME = SettingsKeeper.getSettings()
			.getRHAIroot() + "/" + "data/tmp.out";
	private Abstractor<K> abstractor;
	private DataHandler<Sequence<K>> handlerOut;

	/**
	 * Creates a new instance of this class
	 * 
	 * @param abstractor
	 *            : the effective abstractor to be used, not <code>null</code>
	 * @param handlerOut
	 *            : the {@link DataHandler} instance that will handle the
	 *            extracted sequence, not <code>null</code>
	 */
	public AbstractorHandler(Abstractor<K> abstractor,
			DataHandler<Sequence<K>> handlerOut) {
		super();
		this.abstractor = abstractor;
		this.handlerOut = handlerOut;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.rhai.reading.DataHandler#handle(java.lang.Object)
	 */
	public void handle(Collection<T> data) {
		try {
			Sequence<K> sequence = abstractor
					.buildSequence(writeCollection(data));
			this.handlerOut.handle(sequence);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private File writeCollection(Collection<T> data) throws IOException {
		File file = new File(TMP_FILENAME);
		BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
		for (T element : data) {
			writer.write(element.toString());
			writer.newLine();
		}
		writer.close();
		file.deleteOnExit();
		return file;
	}
}
