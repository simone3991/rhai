package it.rhai.simulation;

import it.rhai.model.PowerMeasure;
import it.rhai.reading.Reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ReaderInvoker {

	private ArrayList<PowerMeasure> data = new ArrayList<PowerMeasure>();
	private Timer timer = new Timer("invoker");
	private Reader<PowerMeasure> reader;
	private int samplingTime;

	public ReaderInvoker(File file, final Reader<PowerMeasure> reader,
		int samplingTime) throws IOException {
		loadData(file);
		this.reader = reader;
		this.samplingTime = samplingTime;
	}

	public void start() {
		timer.schedule(new TimerTask() {

			private int nextData = 0;

			@Override
			public void run() {
				reader.read(data.get(nextData));
				nextData++;
				if (nextData == data.size()) {
					timer.cancel();
				}
			}
		}, samplingTime, samplingTime);
	}

	private void loadData(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		while ((line = reader.readLine()) != null) {
			data.add(PowerMeasure.parsePowerMeasure(line));
		}
		reader.close();
	}
}
