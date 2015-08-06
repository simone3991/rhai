package it.rhai.gui.identification;

import it.rhai.gui.Application;
import it.rhai.gui.ApplicationElement;
import it.rhai.gui.util.WindowsUtils;
import it.rhai.model.PowerMeasure;
import it.rhai.model.RHAILabelEnum.RHAILabel;
import it.rhai.settings.RHAIPropertiesSettings;
import it.rhai.settings.SettingsKeeper;
import it.rhai.simulation.ReaderInvoker;
import it.rhai.simulation.abstraction.AbstractorHandler;
import it.rhai.simulation.abstraction.CumulativeAbstractor;
import it.rhai.simulation.abstraction.JTSAAbstractor;
import it.rhai.simulation.abstraction.JTSARenderedAbstractor;
import it.rhai.simulation.identification.Identifier;
import it.rhai.simulation.reading.RedirectingReader;
import it.rhai.util.DataHandler;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RealTimeIdentifierFrame extends JFrame implements
		ApplicationElement, DataHandler<String>, Observer {

	private static final long serialVersionUID = 1L;
	private static final Color DEFAULT_COLOR = Color.GRAY;
	private static final int DEFAULT_THICKNESS = 5;
	private static final Color ACTIVE_COLOR = Color.GREEN;
	private Application application;
	private ArrayList<JPanel> appliancePanels = new ArrayList<JPanel>();

	public RealTimeIdentifierFrame() {
		super("RHAI - Active Appliance");
		SettingsKeeper.setSettings(new RHAIPropertiesSettings(new File(
				"data/settings/settings.properties")));
		Collection<String> appliances = SettingsKeeper.getSettings()
				.getAvailableAppliances();
		Container panel = new JPanel(new GridLayout(1, appliances.size()));
		super.setContentPane(panel);
		int counter = 0;
		for (String string : appliances) {
			appliancePanels.add(counter, buildAppliancePanel(string));
			panel.add(appliancePanels.get(counter));
			counter++;
		}
		super.pack();
		WindowsUtils.putAtMiddleScreen(this);
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private JPanel buildAppliancePanel(String string) {
		JPanel panel = new JPanel();
		panel.setToolTipText(string);
		panel.setSize(200, 200);
		Image image = SettingsKeeper
				.getSettings()
				.getIcon(string)
				.getScaledInstance(panel.getWidth(), panel.getHeight(),
						Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(image);
		JLabel label = new JLabel(icon);
		label.setSize(panel.getSize());
		panel.add(label);
		panel.setBorder(BorderFactory.createLineBorder(DEFAULT_COLOR,
				DEFAULT_THICKNESS));
		return panel;
	}

	@Override
	public void setApplication(Application application) {
		this.application = application;
	}

	@Override
	public void turnOn() {
		super.setVisible(true);
		startRHAI();
	}

	@Override
	public void turnOff() {
		super.setVisible(false);
	}

	private void startRHAI() {
		ReaderInvoker invoker;
		try {
			RedirectingReader<PowerMeasure> reader = new RedirectingReader<PowerMeasure>(
					new AbstractorHandler<PowerMeasure, RHAILabel>(
							new CumulativeAbstractor<RHAILabel>(
									new JTSAAbstractor(
											new JTSARenderedAbstractor())),
							new Identifier(this, SettingsKeeper.getSettings())));
			invoker = new ReaderInvoker(new File(
					(String) application.getParam("file-path")), reader);
			reader.setMaxLength(SettingsKeeper.getSettings().getTAbstraction()
					/ (invoker.getSamplingTime() / 1000));
			invoker.addObserver(this);
			invoker.start();
		} catch (Exception e) {
			application.exit();
		}
	}

	@Override
	public void handle(String toBeHandled) {
		System.out.println(toBeHandled);
		for (JPanel jPanel : appliancePanels) {
			if (jPanel.getToolTipText().equals(toBeHandled)) {
				jPanel.setBorder(BorderFactory.createLineBorder(ACTIVE_COLOR,
						DEFAULT_THICKNESS));
			} else {
				jPanel.setBorder(BorderFactory.createLineBorder(DEFAULT_COLOR,
						DEFAULT_THICKNESS));
			}
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			SettingsKeeper.getSettings().getDebugLogger()
					.handle(e.getMessage());
		}
		application.next();
	}
}
