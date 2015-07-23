package it.rhai.gui.util;

import it.rhai.gui.Application;
import it.rhai.gui.ApplicationElement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This implementation of {@link ApplicationElement} interface allows the user
 * to either restart or close the application, with two simple buttons
 * 
 * @author simone
 *
 */
public class FinalLayer extends JFrame implements ApplicationElement {

	private static final long serialVersionUID = 1L;
	private Application app;

	/**
	 * Creates a new instance of this class
	 */
	public FinalLayer() {
		super("RHAI - End of Process");
		JPanel panel = new JPanel();
		super.setContentPane(panel);
		JButton restarter = new JButton("Restart");
		restarter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				app.restart();
			}
		});
		panel.add(restarter);
		JButton exiter = new JButton("Close");
		exiter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				app.exit();
			}
		});
		panel.add(exiter);
		super.setSize(400, 60);
		JFrameUtils.putAtMiddleScreen(this);
	}

	@Override
	public void setApplication(Application application) {
		this.app = application;
	}

	@Override
	public void turnOn() {
		super.setVisible(true);
	}

	@Override
	public void turnOff() {
		super.setVisible(false);
	}

}
