package ghost.mods.impl.render;

import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;

public class FullBright extends Mod {

	public FullBright() {
		super("FullBright", "Makes everything light up.", Category.RENDER);
	}

	public float old = -1;
	
	@Override
	public void onEnable() {
		old = mc.gameSettings.gammaSetting;
		mc.gameSettings.gammaSetting = 10f;
		super.onEnable();
	}

	@Override
	public void onDisable() {
		mc.gameSettings.gammaSetting = old;
		super.onDisable();
	}
	
}
