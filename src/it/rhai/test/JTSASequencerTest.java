package it.rhai.test;

import it.rhai.abstraction.JTSAAbstractor;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class JTSASequencerTest {
	
	private static JTSAAbstractor sequencer = new JTSAAbstractor();
	
	@Test
	public void testBuildSequence() throws IOException {
		sequencer.buildSequence(new File("testing.dat"));
	}

}
