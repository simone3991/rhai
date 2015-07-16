package it.rhai.test;

import it.distanciable.Distanciable;
import it.distanciable.sequences.Sequence;
import it.rhai.abstraction.Abstractor;

import java.io.File;

public class TestingAbstractor<T extends Distanciable<T>> implements
		Abstractor<T> {

	private int counter = 1;
	
	@Override
	public Sequence<T> buildSequence(File data) {
		System.out.println("built sequence n°: "+counter);
		counter++;
		return null;
	}

}
