package it.rhai.gui.addition;

import it.rhai.gui.Application;
import it.rhai.gui.ApplicationElement;
import it.rhai.gui.util.WindowsUtils;
import it.rhai.settings.SettingsKeeper;
import it.rhai.simulation.ApplianceAdder;
import it.rhai.util.FileUtils;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ApplianceAdderFrame extends JFrame implements ApplicationElement {

	private static final long serialVersionUID = 8430645396338522139L;
	private Application application;
	protected String applianceFile;
	private JTextField applianceFileInput;
	private JTextField iconFileInput;
	private String iconFile;
	protected Component me = this;

	public ApplianceAdderFrame() {
		JComponent.setDefaultLocale(Locale.ENGLISH);
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.buildMe();
		WindowsUtils.putAtMiddleScreen(this);
	}

	@Override
	public void setApplication(Application application) {
		this.application = application;
	}

	@Override
	public void turnOn() {
		setVisible(true);
	}

	@Override
	public void turnOff() {
		setVisible(false);
	}

	private void buildMe() {
		super.setTitle("RHAI - Appliance Adder");
		super.setSize(600, 120);
		super.setResizable(false);
		super.setLayout(new BorderLayout());
		super.add(new JPanel(), BorderLayout.NORTH);
		super.add(buildForm(), BorderLayout.CENTER);
	}

	private Component buildForm() {
		this.applianceFileInput = buildInput();
		applianceFileInput.setPreferredSize(new Dimension(400, 24));
		Container wrapperInput = new JPanel(new GridLayout(2, 1));
		wrapperInput.add(applianceFileInput);
		this.iconFileInput = buildInputIcon();
		iconFileInput.setPreferredSize(new Dimension(400, 24));
		wrapperInput.add(iconFileInput);
		Container wrapperButton = new JPanel(new GridLayout(3, 1));
		JButton searcher = buildSearcherAppliance();
		searcher.setPreferredSize(new Dimension(200, 24));
		JButton searcher2 = buildSearcherIcon();
		searcher2.setPreferredSize(new Dimension(200, 24));
		JButton starter = buildAdder();
		starter.setPreferredSize(new Dimension(200, 24));
		wrapperButton.add(searcher);
		wrapperButton.add(searcher2);
		wrapperButton.add(starter);
		JPanel form = new JPanel(new BorderLayout());
		form.add(wrapperInput, BorderLayout.CENTER);
		form.add(wrapperButton, BorderLayout.EAST);
		return form;
	}

	private JButton buildSearcherAppliance() {
		JButton button = new JButton("Load appliance's data");
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
					applianceFileInput.setText(selectedFile.getPath());
					applianceFile = selectedFile.getAbsolutePath();
				}
			}
		});
		return button;
	}

	private JButton buildSearcherIcon() {
		JButton button = new JButton("Load an icon");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"Images", "png", "jpg");
				chooser.setFileFilter(filter);
				handleChoice(chooser);
			}

			private void handleChoice(JFileChooser chooser) {
				int returnVal = chooser.showOpenDialog(me);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();
					iconFileInput.setText(selectedFile.getPath());
					iconFile = selectedFile.getAbsolutePath();
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
				applianceFile = inputField.getText();
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		return inputField;
	}

	private JTextField buildInputIcon() {
		final JTextField inputField = new JTextField();
		inputField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				iconFile = inputField.getText();
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		return inputField;
	}

	private JButton buildAdder() {
		JButton button = new JButton("Add the appliance");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ApplianceAdder adder = new ApplianceAdder();
				try {
					File file = new File(applianceFile);
					adder.addAppliance(
							file.getName().replace(
									"." + FileUtils.getExtension(file), ""),
							file, new File(iconFile));
				} catch (IOException e1) {
					SettingsKeeper.getSettings().getDebugLogger()
							.handle(e1.getMessage());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				application.next();
			}
		});
		return button;

	}
}
