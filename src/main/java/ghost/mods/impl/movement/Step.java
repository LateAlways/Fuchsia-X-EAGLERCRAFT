package ghost.mods.impl.movement;

import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;
import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.adapter.EaglerAdapterImpl2;
import net.lax1dude.eaglercraft.glemu.EaglerAdapterGL30;
import net.minecraft.src.Packet11PlayerPosition;

public class Step extends Mod {

	public Step() {
		super("Step", "", Category.MOVEMENT,true);
	}
	
	@Override
	public void onDisable() {
		mc.thePlayer.stepHeight = 0.5f;
	}

	@Override
	public void onUpdate() {
		if((mc.thePlayer.isCollidedHorizontally) && (mc.thePlayer.onGround)) {
			mc.thePlayer.sendQueue.addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.posX, mc.thePlayer.boundingBox.minY+0.42, mc.thePlayer.posY+0.42, mc.thePlayer.posZ, mc.thePlayer.onGround));
			mc.thePlayer.sendQueue.addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.posX, mc.thePlayer.boundingBox.minY+0.72,mc.thePlayer.posY+0.72, mc.thePlayer.posZ, mc.thePlayer.onGround));
			mc.thePlayer.stepHeight = 1.0f;
		}
	}
	
}
