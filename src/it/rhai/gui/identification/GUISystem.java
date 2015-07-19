package it.rhai.gui.identification;

public class GUISystem implements Notifiable {

	private static GUISystem system = new GUISystem();
	private DynamicButton button;
	private DynamicButton original;

	private GUISystem() {
	}

	public static GUISystem getInstance() {
		return system;
	}

	public void registerButton(DynamicButton button) {
		try {
			this.original = (DynamicButton) button.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.button = button;
	}

	@Override
	public void notifyMessage(String text) {
		if (!text.equals("")) {
			button.setTarget(new RHAIStarter());
			button.change();
		}
		else{
			button.setTarget(original);
			button.change();
		}
	}

}
