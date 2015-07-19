package it.rhai.gui.util;

import it.rhai.util.DataHandler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

/**
 * This {@link JTextField} subclass provides all inherit methods but notifies
 * the contained message to a {@link DataHandler} instance whenever a string or
 * a single char is written into itself
 * 
 * @author simone
 *
 */
public class NotifierInputField extends JTextField {

	private static final long serialVersionUID = 1L;
	private DataHandler<String> notifyDest;

	/**
	 * Creates a new instance of this class. From now on, whenever a char is
	 * writtern into the field, this objects automatically alerts the given
	 * handler
	 * 
	 * @param handler
	 *            : the handler to notify the written message to
	 */
	public NotifierInputField(DataHandler<String> handler) {
		this.notifyDest = handler;
		super.setToolTipText("Insert here the path of your file");
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				notifyMessage();
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see javax.swing.text.JTextComponent#setText(java.lang.String)
	 */
	public void setText(String text) {
		super.setText(text);
		notifyMessage();
	}

	private void notifyMessage() {
		notifyDest.handle(getText());
	}
}
