package ghost.mods.impl.misc;

import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;
import net.minecraft.src.Packet10Flying;

public class NoFall extends Mod {

	public NoFall() {
		super("NoFall", "Prevents you from taking fall damage", Category.MISC, true);
	}

	@Override
	public void onUpdate() {
		if(mc.thePlayer.fallDistance > 2 && mc.thePlayer.posY < 150) {
			mc.getNetHandler().addToSendQueue(new Packet10Flying(true));
		}
		super.onUpdate();
	}
	
}
