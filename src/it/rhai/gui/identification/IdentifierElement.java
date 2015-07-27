package it.rhai.gui.identification;

import it.rhai.gui.Application;
import it.rhai.gui.ApplicationElement;
import it.rhai.model.PowerConsumptionLabel;
import it.rhai.model.PowerMeasure;
import it.rhai.settings.FileSettings;
import it.rhai.settings.SettingsKeeper;
import it.rhai.simulation.abstraction.AbstractorHandler;
import it.rhai.simulation.abstraction.CumulativeAbstractor;
import it.rhai.simulation.abstraction.JTSAAbstractor;
import it.rhai.simulation.abstraction.JTSARenderedAbstractor;
import it.rhai.simulation.identification.Identifier;
import it.rhai.simulation.reading.Reader;
import it.rhai.simulation.reading.RedirectingReader;
import it.rhai.util.DataHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class IdentifierElement implements ApplicationElement,
		DataHandler<String> {

	private Application application;
	private ArrayList<PowerMeasure> data = new ArrayList<PowerMeasure>();
	private int nextData = 0;

	@Override
	public void setApplication(Application application) {
		this.application = application;
	}

	@Override
	public void turnOn() {
		try {
			this.loadData(new File((String) application.getParam("file-path")));
			SettingsKeeper.setSettings(new FileSettings(new File(
					"settings/settings.properties")));
		} catch (IOException e) {
			SettingsKeeper.getSettings().getDebugLogger()
					.handle(e.getMessage());
		}
		this.start();
	}

	@Override
	public void turnOff() {
	}

	private void start() {
		Reader<PowerMeasure> reader = new RedirectingReader<PowerMeasure>(
				new AbstractorHandler<PowerMeasure, PowerConsumptionLabel>(
						new CumulativeAbstractor<PowerConsumptionLabel>(
								new JTSAAbstractor(new JTSARenderedAbstractor())),
						new Identifier(this)));
		while (nextData < data.size()) {
			reader.read(data.get(nextData));
			nextData++;
		}
		application.exit(); //TODO: if next() collapses: why?
	}

	private void loadData(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = reader.readLine();
		while (line != null && line.trim() != "") {
			data.add(PowerMeasure.parsePowerMeasure(line));
			line = reader.readLine();
		}
		reader.close();
	}

	@Override
	public void handle(String toBeHandled) {
		//System.out.println(toBeHandled);
	}
}
