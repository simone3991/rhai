package it.rhai.gui.util;

import java.util.HashMap;

public class Application {

	private HashMap<String, Object> applicationParams = new HashMap<String, Object>();
	private ApplicationElement[] elements;
	private int currentElement;

	public Application(ApplicationElement... frames) {
		this.elements = frames;
		for (ApplicationElement element : frames) {
			element.setApplication(this);
		}
	}
	
	public void start() {
		elements[0].setCurrent();
		currentElement = 1;
	}

	public void next() {
		elements[(currentElement - 1)].setOld();
		if (elements.length > currentElement) {
			elements[currentElement].setCurrent();
			currentElement++;
		} else {
			exit();
		}
	}

	public void restart() {
		elements[currentElement - 1].setOld();
		start();
	}

	public void exit() {
		System.exit(0);
	}

	public void setParam(String param, Object value) {
		System.out.println("added param: "+value.toString());
		applicationParams.put(param, value);
	}

	public Object getParam(String param) {
		return applicationParams.get(param);
	}
}
