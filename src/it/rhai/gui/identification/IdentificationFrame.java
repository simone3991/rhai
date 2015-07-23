package it.rhai.gui.identification;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import it.rhai.gui.util.Application;
import it.rhai.gui.util.ApplicationElement;
import it.rhai.gui.util.JFrameUtils;
import it.rhai.model.PowerConsumptionLabel;
import it.rhai.model.PowerMeasure;
import it.rhai.settings.FileSettings;
import it.rhai.settings.SettingsKeeper;
import it.rhai.simulation.ReaderInvoker;
import it.rhai.simulation.abstraction.AbstractorHandler;
import it.rhai.simulation.abstraction.CumulativeAbstractor;
import it.rhai.simulation.abstraction.JTSAAbstractor;
import it.rhai.simulation.abstraction.JTSARenderedAbstractor;
import it.rhai.simulation.identification.Identifier;
import it.rhai.simulation.reading.RedirectingReader;
import it.rhai.util.DataHandler;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class IdentificationFrame extends JFrame implements ApplicationElement,
		DataHandler<String>, Observer {

	private static final long serialVersionUID = 1L;
	private Application application;
	private JLabel sample = new JLabel();
	private ArrayList<JLabel> labels = new ArrayList<JLabel>();

	public IdentificationFrame() {
		super("RHAI - Active Appliance");
		SettingsKeeper.setSettings(new FileSettings(new File(
				"settings/settings.properties")));
		Collection<String> appliances = SettingsKeeper.getSettings()
				.getAvailableAppliances();
		Container panel = new JPanel(new GridLayout(appliances.size(), 1));
		super.setContentPane(panel);
		int counter = 0;
		for (String string : appliances) {
			JLabel label = new JLabel(string);
			label.setHorizontalAlignment(JLabel.CENTER);
			labels.add(counter, label);
			panel.add(label);
			counter++;
		}
		super.setSize(400, 500);
		JFrameUtils.putAtMiddleScreen(this);
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
			invoker = new ReaderInvoker(
					new File((String) application.getParam("file-path")),
					new RedirectingReader<PowerMeasure>(
							new AbstractorHandler<PowerMeasure, PowerConsumptionLabel>(
									new CumulativeAbstractor<PowerConsumptionLabel>(
											new JTSAAbstractor(
													new JTSARenderedAbstractor())),
									new Identifier(this))), 100);

			invoker.addObserver(this);
			invoker.start();
		} catch (IOException e) {
		}
	}

	@Override
	public void handle(String toBeHandled) {
		for (JLabel jLabel : labels) {
			if (jLabel.getText().equals(toBeHandled)) {
				jLabel.setForeground(Color.RED);
				jLabel.setFont(new Font(sample.getFont().getName(), sample
						.getFont().getStyle(), sample.getFont().getSize() * 3));
			} else {
				jLabel.setForeground(sample.getForeground());
				jLabel.setFont(sample.getFont());
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
