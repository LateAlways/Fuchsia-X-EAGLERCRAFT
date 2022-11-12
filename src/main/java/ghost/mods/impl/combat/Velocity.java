package ghost.mods.impl.combat;

import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;
import ghost.mods.essential.settings.BooleanSetting;
import ghost.mods.essential.settings.NumberSetting;

public class Velocity extends Mod {

	public NumberSetting vertical = new NumberSetting("Vertical Knockback", "", 1F, 1F, 100F, 1F,1F);
	public NumberSetting horizontal = new NumberSetting("Horizontal Knockback", "", 1F, 1F, 100F, 1F,1F);
	public BooleanSetting novelocity = new BooleanSetting("No velocity", "Disables velocity", false);
	
	public Velocity() {
		super("Velocity","Reduce knockback taken.", Category.COMBAT);
		addSetting(vertical,horizontal,novelocity);
	}
	
}
