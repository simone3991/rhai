package it.rhai.util;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

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
		this.putAtMiddleScreen();
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

	private void putAtMiddleScreen() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		super.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2);
	}

}
