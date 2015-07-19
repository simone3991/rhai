package it.rhai.gui.identification;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

public class NotifierInputField extends JTextField {

	private static final long serialVersionUID = 1L;
	private Notifiable notifyDest;
	
	public NotifierInputField(Notifiable notifiable) {
		this.notifyDest = notifiable;
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
	public void setText(String text) {
		super.setText(text);
		notifyMessage();
	}

	private void notifyMessage() {
		notifyDest.notifyMessage(getText());
	}
}
