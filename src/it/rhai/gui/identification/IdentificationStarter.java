package it.rhai.gui.identification;

import it.rhai.gui.Application;

import javax.swing.UIManager;

import com.seaglasslookandfeel.SeaGlassLookAndFeel;

public class IdentificationStarter {

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(new SeaGlassLookAndFeel());
		Application app = new Application(new FileSearchingFrame(),
				new IdentifierElement());
		app.start();
	}

}
