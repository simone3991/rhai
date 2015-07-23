package it.rhai.gui.util;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

/**
 * A collection of algorithms to manage {@link JFrame} windows
 * 
 * @author simone
 *
 */
public class JFrameUtils {

	/**
	 * Puts a {@link JFrame} window at the center of the screen
	 * 
	 * @param frame
	 *            : the frame whose location is to be set
	 */
	public static void putAtMiddleScreen(JFrame frame) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height
				/ 2 - frame.getSize().height / 2);
	}
}
