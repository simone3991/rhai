package it.rhai.test;

import static org.junit.Assert.assertEquals;
import it.rhai.settings.FileSettings;

import java.io.File;

import org.junit.Test;

public class FileSettingsTest {

	FileSettings settings = new FileSettings(new File("settings/settings.properties"));

	@Test
	public void testGetMinimumLikelihood() {
		assertEquals(settings.getMinimumLikelihood(), -1, 0);
	}

	@Test
	public void testGetTAbstraction() {
		assertEquals(13, settings.getTAbstraction(), 0);
	}

}
