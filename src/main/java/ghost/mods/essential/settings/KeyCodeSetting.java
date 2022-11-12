package ghost.mods.essential.settings;

public class KeyCodeSetting extends Setting {

	public int keyCode;
	
	public boolean clickGuiFocused = false;
	
	public boolean debounceRemoveKeyCode = false;
	
	public KeyCodeSetting(String name, String description, int key) {
		super(name, description);
		this.keyCode = key;
	}

	public int getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}
	
}
