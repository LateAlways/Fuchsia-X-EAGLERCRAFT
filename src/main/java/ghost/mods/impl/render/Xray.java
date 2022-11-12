package ghost.mods.impl.render;

import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;

public class Xray extends Mod {

	public Xray() {
		super("XRay", "See only important blocks", Category.RENDER);
	}

	
	@Override
	public void onEnable() {
		mc.renderGlobal.loadRenderers();
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		mc.renderGlobal.loadRenderers();
		super.onDisable();
	}
}
