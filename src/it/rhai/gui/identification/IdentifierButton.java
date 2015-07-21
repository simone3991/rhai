package it.rhai.gui.identification;

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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;

public class IdentifierButton extends JButton {

	private static final long serialVersionUID = 1L;

	public IdentifierButton() {
		super("Start the simulation");
		super.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				File file = IdentificationGUISystem.getInstance().getDataFile();
				SettingsKeeper.setSettings(new FileSettings(new File(
						"settings/settings.properties")));
				ReaderInvoker invoker;
				try {
					invoker = new ReaderInvoker(
							file,
							new RedirectingReader<PowerMeasure>(
									new AbstractorHandler<PowerMeasure, PowerConsumptionLabel>(
											new CumulativeAbstractor<PowerConsumptionLabel>(
													new JTSAAbstractor(
															new JTSARenderedAbstractor())),
											new Identifier())), 100);
					invoker.start();
				} catch (IOException e1) {
					SettingsKeeper.getSettings().getDebugLogger()
							.handle(e1.getMessage());
				}
			}
		});
	}
}
