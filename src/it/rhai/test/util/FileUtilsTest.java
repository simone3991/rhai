package it.rhai.test.util;

import it.rhai.util.FileUtils;

import java.io.File;

import org.junit.Test;

public class FileUtilsTest {

	@Test
	public void testCountFiles() {
		System.out.println(FileUtils.countFiles(new File("/home/simone/Scrivania/RHAI_db/dataset/training")));
	}

}
