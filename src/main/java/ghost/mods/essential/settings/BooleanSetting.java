package ghost.mods.essential.settings;

public class BooleanSetting extends Setting {
	
	public boolean value;
	
	public BooleanSetting(String name, String description, boolean value) {
		super(name, description);
		this.value = value;
	}
	public BooleanSetting(String name, String description) {
		super(name, description);
	}
	
	
	public boolean getValue() {
		return value;
	}
	
	public void setValue(boolean value) {
		this.value = value;
	}

}
