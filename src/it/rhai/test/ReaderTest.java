package it.rhai.test;

import static org.junit.Assert.*;
import it.rhai.reading.DataHandler;
import it.rhai.reading.Reader;
import it.rhai.reading.RedirectingReader;
import it.rhai.settings.SettingsKeeper;

import org.junit.Test;

public class ReaderTest {

	DataHandler<Double, Object> dataHandler = new TestingDataHandler<Double, Object>();
	Reader<Double> reader = new RedirectingReader<Double>(dataHandler);
	
	@Test
	public void testRead() throws Exception{
		reader.read(0.98);
		reader.read(398.9);
		reader.read(97.7);
	}

	@Test
	public void testSend() throws Exception {
		assertTrue(dataHandler.getHandled()==null);
		for (int i = 0; i < SettingsKeeper.getSettings().getTAbstraction(); i++) {
			reader.read(0.0);
		}
		assertTrue(dataHandler.getHandled()!=null);
	}
}
