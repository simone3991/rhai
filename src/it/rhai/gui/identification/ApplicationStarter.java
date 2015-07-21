package it.rhai.gui.identification;

import it.rhai.util.ActiveApplianceRealTimeDisplayer;
import it.rhai.util.Outputs;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.seaglasslookandfeel.SeaGlassLookAndFeel;

public class ApplicationStarter {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new SeaGlassLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		Outputs.putOutput("gui", new ActiveApplianceRealTimeDisplayer());
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
	}
}
