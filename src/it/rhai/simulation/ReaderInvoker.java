package it.rhai.simulation;

import it.rhai.model.PowerMeasure;
import it.rhai.simulation.reading.Reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class simulates a real-time data acquisition by establishing a fixed
 * samplig time and then invoking a reader each sampling time in order to
 * simulate the real-time reading. The data are pre-loaded from a source data
 * file
 * 
 * @author simone
 *
 */
public class ReaderInvoker {

	private ArrayList<PowerMeasure> data = new ArrayList<PowerMeasure>();
	private Timer timer = new Timer("invoker");
	private Reader<PowerMeasure> reader;
	private int samplingTime;

	/**
	 * Creates a new instance of this class
	 * 
	 * @param file
	 *            : the file where the data will be taken from
	 * @param reader
	 *            : the reader to invoke
	 * @param samplingTime
	 *            : the simulated sampling time, i.e. the delay that the invoker
	 *            will wait to call thre reader
	 * @throws IOException
	 *             : if file cannot be opened
	 */
	public ReaderInvoker(File file, final Reader<PowerMeasure> reader,
			int samplingTime) throws IOException {
		loadData(file);
		this.reader = reader;
		this.samplingTime = samplingTime;
	}

	/**
	 * Lets the simulation start
	 */
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
