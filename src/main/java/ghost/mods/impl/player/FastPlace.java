package ghost.mods.impl.player;

import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;
import ghost.mods.essential.settings.NumberSetting;

public class FastPlace extends Mod {

	public NumberSetting delay = new NumberSetting("Delay", "Click delay", 0f,0f,4f,1f,1f);
	
	public FastPlace() {
		super("FastPlace", "Places blocks faster just by holding right click.", Category.PLAYER, true);
		addSetting(delay);
	}

}
