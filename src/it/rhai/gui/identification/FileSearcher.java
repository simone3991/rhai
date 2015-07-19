package it.rhai.gui.identification;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "Source Data Files", "dat", "csv");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(myParent);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			       displayer.setText(chooser.getSelectedFile().getPath());
			    }
			}
		});
	}
	
	@Override
	protected FileSearcher clone() throws CloneNotSupportedException {
		return new FileSearcher(displayer);
	}

}
