package it.rhai.gui.identification;

import it.rhai.gui.util.FileSearcher;
import it.rhai.gui.util.NotifierInputField;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Locale;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public MainFrame() {
		JComponent.setDefaultLocale(Locale.ENGLISH);
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.buildMe();
		this.putAtMiddleScreen();
	}

	private void buildMe() {
		super.setTitle("RHAI - Running Household Appliances Identifier");
		super.setSize(650, 80);
		super.setResizable(false);
		super.setLayout(new BorderLayout());
		super.add(new JPanel(), BorderLayout.NORTH);
		super.add(buildForm(), BorderLayout.CENTER);
	}

	private Component buildForm() {
		JTextField inputField = new NotifierInputField(IdentificationGUISystem.getInstance());
		inputField.setPreferredSize(new Dimension(400, 24));
		Container wrapperInput = new JPanel();
		wrapperInput.add(inputField);
		Container wrapperButton = new JPanel();
		FileSearcher button = new FileSearcher(inputField);
		button.setPreferredSize(new Dimension(180, 24));
		wrapperButton.add(button);
		IdentificationGUISystem.getInstance().registerButton(button);
		JPanel form = new JPanel(new BorderLayout());
		form.add(wrapperInput, BorderLayout.CENTER);
		form.add(wrapperButton, BorderLayout.EAST);
		return form;
	}

	private void putAtMiddleScreen() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		super.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2);
	}
}
