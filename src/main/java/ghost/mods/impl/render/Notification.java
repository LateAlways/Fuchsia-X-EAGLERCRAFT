package ghost.mods.impl.render;

import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;
import ghost.mods.essential.settings.BooleanSetting;

public class Notification extends Mod {

	public BooleanSetting modtoggles = new BooleanSetting("Mod Toggles", "Notify on mod toggles.", true);
	
	public Notification() {
		super("Notifications", "Notifies when you enable/disable a feature.", Category.RENDER);
		addSetting(modtoggles);
	}
	

}
