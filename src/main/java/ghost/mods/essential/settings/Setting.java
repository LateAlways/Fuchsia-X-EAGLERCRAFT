package ghost.mods.essential.settings;

import ghost.mods.essential.Mod;

public class Setting {
	
	public String name;
	public String description;
	public Mod mod;
	
	public Mod getMod() {
		return mod;
	}

	public void setMod(Mod mod) {
		this.mod = mod;
	}

	public boolean shouldDraw() {
		return draw;
	}
	
	public boolean draw = true;
	
	public void setDraw(boolean draw) {
		this.draw = draw;
	}

	public boolean clickguiDebounce = false;
	
	public Setting(String name, String description) {
		this.name = name;
		this.description = description;
	}

}
