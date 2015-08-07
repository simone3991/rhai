package it.rhai.settings;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * {@link RHAISettings} represents a generic interface through which all needed
 * informations about the framework can be taken
 * 
 * @author simone
 *
 */
public interface RHAISettings extends RHAIdentificationSettings,
		RHAIUtilSettings, RHAIAbstractionSettings {

	/**
	 * Returns the root directory of the RHAI environment
	 * 
	 * @return
	 */
	public String getRHAIroot();
	
	/**
	 * Returns an icon representation for a certain appliance
	 * 
	 * @param appliance
	 *            : the appliance whose representation is required
	 * 
	 * @return: an {@link ImageIcon} instance representing that appliance
	 */
	public Image getIcon(String appliance);

}
