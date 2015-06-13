package it.rhai.identification;

import java.io.File;

import util.distanciables.Distanciable;
import model.Sequence;

public interface Sequencer<T extends Distanciable<T>> {

	public Sequence<T> buildSequence(File data);

}
