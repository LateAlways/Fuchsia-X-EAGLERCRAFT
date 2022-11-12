package ghost.mods.essential;

public enum Category {
	
	COMBAT("Combat"),
	MOVEMENT("Movement"),
	PLAYER("Player"),
	RENDER("Render"),
	WORLD("World"),
	MISC("Misc"),
	CategoryList("CategoryList");

	public String name;
	public int lastX, lastY;
	public boolean dragging = false;
	public int x,y;
	public boolean collapsed = true;
	public boolean shown = false;
	public Mod moduleSettingsWatching;
	public int maxWidth = 140;
	public boolean hideDebounce = false;
	public boolean moveDebounce = false;
	public boolean debounceBackButton = false;
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	Category(String name) {
		this.name = name;
		if(name == "CategoryList") {
			this.maxWidth = 110;
			this.x = 10;
			this.y = 10;
			this.shown = true;
			this.collapsed = false;
		}
	}

}
