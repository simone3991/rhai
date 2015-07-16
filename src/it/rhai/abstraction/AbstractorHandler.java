package it.rhai.abstraction;

import it.distanciable.Distanciable;
import it.distanciable.sequences.Sequence;
import it.rhai.reading.DataHandler;

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
 * this class converts a generic collection of data element into a
 * {@link Sequence} of different type objects, and eventually let it be handled
 * by an other handler
 * 
 * @author simone
 *
 * @param <T>: the type of data to be handled
 * @param <K>: the type of the sequence to be extracted, and, therefore, the
 *        type of the data to be handled after this block
 */
public class AbstractorHandler<T, K extends Distanciable<K>> implements
		DataHandler<Collection<T>> {

	private static final String TMP_FILENAME = "tmp.out";
	private Abstractor<K> abstractor;
	private DataHandler<Sequence<K>> handlerOut;

	/**
	 * Creates a new instance of this class
	 * 
	 * @param abstractor
	 *            : the effective abstractor to be used
	 * @param handlerOut
	 *            : the {@link DataHandler} instance that will handle the
	 *            extracted sequence
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
	 * @see it.rhai.reading.DataHandler#handle(java.lang.Object)
	 */
	public void handle(Collection<T> data) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
					TMP_FILENAME)));
			for (T powerMeasure : data) {
				writer.write(powerMeasure.toString());
				writer.newLine();
			}
			writer.close();
			Sequence<K> sequence = abstractor.buildSequence(new File(
					TMP_FILENAME));
			this.handlerOut.handle(sequence);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
