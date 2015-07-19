package it.rhai.gui.identification;

import javax.swing.JButton;

public class DynamicButton extends JButton{

	private static final long serialVersionUID = 1L;
	private ButtonChanger changer;

	public void setTarget(JButton target){
		this.changer = new ButtonChanger(this, target);
	}
	
	public void change(){
		changer.change();
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
