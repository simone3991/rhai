package it.rhai.gui.identification;

import java.awt.Container;
import java.io.File;
import java.io.IOException;

import it.rhai.gui.util.Application;
import it.rhai.gui.util.ApplicationElement;
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

public class IdentificationFrame extends JFrame implements ApplicationElement, DataHandler<String> {

	private static final long serialVersionUID = 1L;
	private Application application;
	private JLabel label;

	public IdentificationFrame() {
		super("RHAI - Active Appliance");
		super.setSize(400, 50);
		this.label = new JLabel("");
		Container panel = new JPanel();
		panel.add(label);
		super.setContentPane(panel);
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
		SettingsKeeper.setSettings(new FileSettings(new File(
				"settings/settings.properties")));
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

			invoker.start();
		} catch (IOException e) {
		}
	}

	@Override
	public void handle(String toBeHandled) {
		this.label.setText(toBeHandled);
	}
}
