package it.rhai.gui.addition;

import it.rhai.gui.Application;

import javax.swing.UIManager;

import com.seaglasslookandfeel.SeaGlassLookAndFeel;

public class ApplicationStarter {

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(new SeaGlassLookAndFeel());
		Application app = new Application(new ApplianceAdderFrame());
		app.start();
	}

}
