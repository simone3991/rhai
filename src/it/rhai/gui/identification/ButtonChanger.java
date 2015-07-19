package it.rhai.gui.identification;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonChanger {
	
	private JButton original;
	private JButton target;

	public ButtonChanger(JButton original, JButton target) {
		this.original = original;
		this.target = target;
	}
	
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
