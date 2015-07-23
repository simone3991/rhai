package it.rhai.gui.util;

import java.util.HashMap;

/**
 * A generic Java Application. This class provides multiple
 * {@link ApplicationElement} app's architecture, and manage to handle theese
 * layers. In order to let the app work, it also provides param connection
 * between a layer and another
 * 
 * @author simone
 *
 */
public class Application {

	private HashMap<String, Object> applicationParams = new HashMap<String, Object>();
	private ApplicationElement[] elements;
	private int currentElement;

	/**
	 * Creates a new app, with a certain set of layers
	 * 
	 * @param layers
	 *            : the {@link ApplicationElement} instances that, in order,
	 *            will represent the application's levels
	 */
	public Application(ApplicationElement... layers) {
		this.elements = layers;
		for (ApplicationElement element : layers) {
			element.setApplication(this);
		}
	}

	/**
	 * Lets the app start. This means that the first {@link ApplicationElement}
	 * will be shown
	 * 
	 * @see ApplicationElement#turnOn()
	 */
	public void start() {
		elements[0].turnOn();
		currentElement = 1;
	}

	/**
	 * Levels up. Thus, the current will be shut down and the next will be shown
	 */
	public void next() {
		elements[(currentElement - 1)].turnOff();
		if (elements.length > currentElement) {
			elements[currentElement].turnOn();
			currentElement++;
		} else {
			exit();
		}
	}

	/**
	 * Restarts the app: the first layer will thus be loaded
	 */
	public void restart() {
		elements[currentElement - 1].turnOff();
		start();
	}

	/**
	 * Closes the application: this operation is unrevokable
	 */
	public void exit() {
		System.exit(0);
	}

	/**
	 * Sets a param of the application
	 * 
	 * @param param
	 *            : a string representing the parameter
	 * @param value
	 *            : the parameter itself
	 */
	public void setParam(String param, Object value) {
		applicationParams.put(param, value);
	}

	/**
	 * Returns a parameter collected in this app
	 * 
	 * @param param
	 *            : the name of the required parameter
	 * @return: the parameter instance
	 */
	public Object getParam(String param) {
		return applicationParams.get(param);
	}
}
