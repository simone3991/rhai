package it.rhai.gui;

/**
 * A generic layer of an {@link Application}
 * 
 * @author simone
 * @see Application
 *
 */
public interface ApplicationElement {

	/**
	 * Sets the container application
	 * 
	 * @param application
	 *            : the app this layer belongs to
	 */
	public void setApplication(Application application);

	/**
	 * Turns on this layer
	 */
	public void turnOn();

	/**
	 * Turns off this layer
	 */
	public void turnOff();

}
