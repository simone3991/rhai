package it.rhai.gui.identification;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class IdentifierButton extends JButton {

	private static final long serialVersionUID = 1L;

	public IdentifierButton() {
		super("Start the simulation");
		super.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("starting");				
			}
		});
	}
}
