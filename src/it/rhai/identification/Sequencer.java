package it.rhai.identification;

import it.distanciable.Distanciable;
import it.distanciable.sequences.Sequence;

import java.io.File;

public interface Sequencer<T extends Distanciable<T>> {

	public Sequence<T> buildSequence(File data);

}
