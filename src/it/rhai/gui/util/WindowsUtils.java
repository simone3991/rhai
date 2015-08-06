package it.rhai.gui.util;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JFrame;

/**
 * A collection of algorithms to manage {@link Window} windows
 * 
 * @author simone
 *
 */
public class WindowsUtils {

	/**
	 * Puts a {@link JFrame} window at the center of the screen
	 * 
	 * @param window
	 *            : the window whose location is to be set
	 */
	public static void putAtMiddleScreen(Window window) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(dim.width / 2 - window.getSize().width / 2, dim.height
				/ 2 - window.getSize().height / 2);
	}
}
