package it.rhai.gui.util;

import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * This class substitutes a {@link JButton} intance with another instance at
 * run-time.
 * 
 * @author simone
 *
 */
public class ButtonChanger {

	private JButton original;
	private JButton target;

	/**
	 * Creates a new instance of this class
	 * 
	 * @param original
	 *            : the button to be substituted
	 * @param target
	 *            : the {@link JButton} instance to: inject into the original
	 *            one when the {@link #change()} method is called
	 */
	public ButtonChanger(JButton original, JButton target) {
		this.original = original;
		this.target = target;
	}

	/**
	 * Changes the {@link JButton} instance, injecting a new one
	 */
	public void change() {
		inheritProperties();
	}

	private void inheritProperties() {
		original.setText(target.getText());
		inheritBehavior();
	}

	private void inheritBehavior() {
		for (ActionListener listener : original.getActionListeners()) {
			original.removeActionListener(listener);
		}
		for (ActionListener listener : target.getActionListeners()) {
			original.addActionListener(listener);
		}
	}

}
