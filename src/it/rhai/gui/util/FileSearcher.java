package it.rhai.gui.util;

import it.rhai.gui.identification.IdentificationGUISystem;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.JTextComponent;

public class FileSearcher extends DynamicButton {

	private static final long serialVersionUID = 1L;
	private Container myParent;
	private JTextComponent displayer;

	public FileSearcher(JTextComponent outputDisplayer) {
		this.myParent = getParent();
		this.setText("Search");
		this.displayer = outputDisplayer;
		this.addActionListener(new ActionListener() {

			@Override
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * java.awt.event.ActionListener#actionPerformed(java.awt.event.
			 * ActionEvent)
			 */
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"Source Data Files", "dat", "csv");
				chooser.setFileFilter(filter);
				handleChoice(chooser);
			}

			private void handleChoice(JFileChooser chooser) {
				int returnVal = chooser.showOpenDialog(myParent);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();
					displayer.setText(selectedFile.getPath());
					IdentificationGUISystem.getInstance().setDataFile(selectedFile);
				}
			}
		});
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.rhai.gui.util.DynamicButton#clone()
	 */
	public FileSearcher clone() throws CloneNotSupportedException {
		return new FileSearcher(displayer);
	}

}
