package it.rhai.abstraction;

import it.distanciable.Distanciable;
import it.distanciable.sequences.Sequence;
import it.rhai.reading.DataHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public class AbstractorHandler<T, K extends Distanciable<K>> implements
		DataHandler<Collection<T>> {

	private static final String TMP_FILENAME = "tmp.out";
	private Abstractor<K> abstractor;
	private DataHandler<Sequence<K>> handlerOut;

	public AbstractorHandler(Abstractor<K> abstractor, DataHandler<Sequence<K>> handlerOut) {
		super();
		this.abstractor = abstractor;
		this.handlerOut = handlerOut;
	}

	@Override
	public void handle(Collection<T> data) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
					TMP_FILENAME)));
			for (T powerMeasure : data) {
				writer.write(powerMeasure.toString());
				writer.newLine();
			}
			writer.close();
			Sequence<K> sequence = abstractor.buildSequence(new File(TMP_FILENAME));
			this.handlerOut.handle(sequence);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
