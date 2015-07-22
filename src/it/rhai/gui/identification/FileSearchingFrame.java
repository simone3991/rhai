package it.rhai.gui.identification;

import it.rhai.gui.util.Application;
import it.rhai.gui.util.ApplicationElement;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileSearchingFrame extends JFrame implements ApplicationElement {

	private static final long serialVersionUID = 1L;
	private Application application;
	private String filePath;
	private Component me = this;
	private JTextField inputField;

	public FileSearchingFrame() {
		JComponent.setDefaultLocale(Locale.ENGLISH);
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.buildMe();
		this.putAtMiddleScreen();
	}

	private void buildMe() {
		super.setTitle("RHAI - Launcher");
		super.setSize(600, 100);
		super.setResizable(false);
		super.setLayout(new BorderLayout());
		super.add(new JPanel(), BorderLayout.NORTH);
		super.add(buildForm(), BorderLayout.CENTER);
	}

	private Component buildForm() {
		this.inputField = buildInput();
		inputField.setPreferredSize(new Dimension(400, 24));
		Container wrapperInput = new JPanel(new GridLayout(2, 1));
		wrapperInput.add(inputField);
		JLabel label = new JLabel(
				"Insert a valid data file to start the identification");
		label.setHorizontalAlignment(JLabel.CENTER);
		wrapperInput.add(label);
		Container wrapperButton = new JPanel(new GridLayout(2, 1));
		JButton searcher = buildSearcher();
		searcher.setPreferredSize(new Dimension(180, 24));
		JButton starter = buildStarter();
		starter .setPreferredSize(new Dimension(180, 24));
		wrapperButton.add(searcher);
		wrapperButton.add(starter);
		JPanel form = new JPanel(new BorderLayout());
		form.add(wrapperInput, BorderLayout.CENTER);
		form.add(wrapperButton, BorderLayout.EAST);
		return form;
	}

	private JButton buildStarter() {
		JButton button = new JButton("Start the simulation");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				application.setParam("file-path", filePath);
				application.next();
			}
		});
		return button;
		
	}

	private JButton buildSearcher() {
		JButton button = new JButton("Search for file");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"Source Data Files", "dat", "csv");
				chooser.setFileFilter(filter);
				handleChoice(chooser);
			}

			private void handleChoice(JFileChooser chooser) {
				int returnVal = chooser.showOpenDialog(me);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();
					inputField.setText(selectedFile.getPath());
					filePath = selectedFile.getAbsolutePath();
				}
			}
		});
		return button;
	}

	private JTextField buildInput() {
		final JTextField inputField = new JTextField();
		inputField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				filePath = inputField.getText();
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		return inputField;
	}

	private void putAtMiddleScreen() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		super.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2);
	}

	@Override
	public void setApplication(Application application) {
		this.application = application;
	}

	@Override
	public void setCurrent() {
		setVisible(true);
	}

	@Override
	public void setOld() {
		setVisible(false);
	}
}
