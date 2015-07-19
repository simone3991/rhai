package it.rhai.gui.identification;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public MainFrame() {
		this.buildMe();
	}

	private void buildMe() {
		super.setTitle("RHAI - Running Household Appliances Identifier");
		super.setSize(420, 80);
		super.setResizable(false);
		super.setLayout(new BorderLayout());
		super.add(new JPanel(), BorderLayout.NORTH);
		super.add(buildLabel(), BorderLayout.SOUTH);
		super.add(buildForm(), BorderLayout.CENTER);
	}

	private Component buildForm() {
		JTextField inputField = new JTextField();
		inputField.setPreferredSize(new Dimension(200, 24));
		Container wrapperInput = new JPanel();
		wrapperInput.add(inputField);
		Container wrapperButton = new JPanel();
		wrapperButton.add(new JButton("Search"));
		JPanel form = new JPanel(new BorderLayout());
		form.add(wrapperInput, BorderLayout.CENTER);
		form.add(wrapperButton, BorderLayout.EAST);
		return form;
	}

	private Component buildLabel() {
		JLabel label = new JLabel("Insert the path of the power measures file");
		label.setHorizontalAlignment(JLabel.CENTER);
	    return label;
	}
}
