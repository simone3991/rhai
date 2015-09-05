package it.rhai.util;

import java.io.File;
import java.io.FileFilter;

/**
 * This simple implementation of {@link FileFilter} accepts only directiories
 * 
 * @author simone
 *
 */
public class DirectoryOnlyFilter implements FileFilter {

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.FileFilter#accept(java.io.File)
	 */
	public boolean accept(File file) {
		return file.isDirectory();
	}

}
