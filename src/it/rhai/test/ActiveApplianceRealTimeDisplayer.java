package it.rhai.test;

import it.rhai.gui.util.WindowsUtils;
import it.rhai.util.DataHandler;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ActiveApplianceRealTimeDisplayer extends JFrame implements
		DataHandler<String> {

	private static final long serialVersionUID = 1L;
	private JLabel label = new JLabel("");;

	public ActiveApplianceRealTimeDisplayer() {
		super("RHAI - Active Appliance");
		JPanel panel = new JPanel();
		label.setFont(new Font(label.getFont().getName(), label.getFont()
				.getStyle(), label.getFont().getSize() * 3));
		panel.add(label);
		super.setContentPane(panel);
		super.setVisible(true);
		super.setSize(500, 80);
		WindowsUtils.putAtMiddleScreen(this);
	}

	@Override
	public void handle(final String toBeHandled) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				label.setText(toBeHandled);
			}
		});
		thread.start();
	}
}
