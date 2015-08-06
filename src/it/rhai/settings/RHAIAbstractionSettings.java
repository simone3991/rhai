package it.rhai.settings;

import it.rhai.model.RHAILabelEnum.RHAILabel;

/**
 * This interface provides simple methods to access all needed informations
 * about RHAI abstraction
 * 
 * @author simone
 *
 */
public interface RHAIAbstractionSettings {

	/**
	 * Returns the seconds to be asbtracted into a single {@link RHAILabel}
	 * 
	 * @return
	 */
	public int getTAbstraction();
}
