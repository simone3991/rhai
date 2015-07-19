package it.rhai.gui.util;

import javax.swing.JButton;

/**
 * This class represents a {@link JButton} object that allows run-time changing
 * 
 * @author simone
 * 
 * @see ButtonChanger
 *
 */
public class DynamicButton extends JButton {

	private static final long serialVersionUID = 1L;
	private ButtonChanger changer;

	/**
	 * Sets the button to be injected into this one when the {@link #change()}
	 * method is called
	 * 
	 * @param target
	 *            : the button instance that this object will become when change
	 *            occurs
	 */
	public void setTarget(JButton target) {
		this.changer = new ButtonChanger(this, target);
	}

	/**
	 * Replaces this object with another one. To set the target button, just
	 * call {@link #setTarget(JButton)}. This operation is provided and
	 * performed by {@link ButtonChanger} class
	 */
	public void change() {
		changer.change();
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public DynamicButton clone() throws CloneNotSupportedException {
		return (DynamicButton) super.clone();
	}
}
