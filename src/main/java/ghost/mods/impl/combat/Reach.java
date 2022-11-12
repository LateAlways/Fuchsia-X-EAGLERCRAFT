package ghost.mods.impl.combat;

import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;
import ghost.mods.essential.settings.BooleanSetting;
import ghost.mods.essential.settings.NumberSetting;

public class Reach extends Mod {

	public NumberSetting reach = new NumberSetting("Reach","", 4.0F, 1.0F, 6.0F,0.5F,0.5F);
	public BooleanSetting test = new BooleanSetting("Setting", "",false);
	
	public Reach() {
		super("Reach", "Extends attack reach", Category.COMBAT);
		addSetting(reach);
	}
	
}
