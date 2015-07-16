package it.rhai.abstraction;

import it.distanciable.Distanciable;
import it.rhai.reading.DataHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public class HandlerAbstractorAdapter<T, K extends Distanciable<K>> implements
		DataHandler<T> {

	private static final String TMP_FILENAME = "tmp.out";
	private DataHandler<K> out;
	private Abstractor<K> abstractor;

	public HandlerAbstractorAdapter(DataHandler<K> out, Abstractor<K> abstractor) {
		super();
		this.out = out;
		this.abstractor = abstractor;
	}

	@Override
	public void handle(Collection<T> data) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
					TMP_FILENAME)));
			for (T powerMeasure : data) {
				writer.write(powerMeasure.toString());
				System.out.println("writing "+powerMeasure.toString());
			}
			writer.close();
//			Sequence<K> sequence = abstractor.buildSequence(new File(
//					TMP_FILENAME));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
