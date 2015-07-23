package it.rhai.gui.identification;

import it.rhai.gui.Application;
import it.rhai.gui.util.FinalLayer;

import javax.swing.UIManager;

import com.seaglasslookandfeel.SeaGlassLookAndFeel;

public class ApplicationStarter {

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(new SeaGlassLookAndFeel());
		Application app = new Application(new FileSearchingFrame(),
				new IdentificationFrame(), new FinalLayer());
		app.start();
	}
}
