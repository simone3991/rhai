package it.rhai.gui.identification;

import it.rhai.gui.util.DynamicButton;
import it.rhai.settings.SettingsKeeper;
import it.rhai.util.DataHandler;

import java.io.File;

public class IdentificationGUISystem implements DataHandler<String> {

	private static IdentificationGUISystem system = new IdentificationGUISystem();
	private DynamicButton button;
	private DynamicButton original;
	private File dataFile;

	private IdentificationGUISystem() {
	}

	public static IdentificationGUISystem getInstance() {
		return system;
	}

	public void registerButton(DynamicButton button) {
		try {
			this.original = (DynamicButton) button.clone();
		} catch (CloneNotSupportedException e) {
			SettingsKeeper.getSettings().getDebugLogger().handle(e.getMessage());
		}
		this.button = button;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see it.rhai.reading.DataHandler#handle(java.lang.Object)
	 */
	public void handle(String text) {
		if (!text.equals("")) {
			button.setTarget(new IdentifierButton());
			button.change();
		} else {
			button.setTarget(original);
			button.change();
		}
	}

	public void setDataFile(File dataFile) {
		this.dataFile = dataFile;
	}
	
	public File getDataFile() {
		return dataFile;
	}

}
