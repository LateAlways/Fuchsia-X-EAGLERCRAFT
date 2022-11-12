package ghost.mods.impl.movement;

import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;

public class Spider extends Mod {

	public Spider() {
		super("Spider", "Lets you climb on walls like if it was a ladder.", Category.MOVEMENT,true);
	}

	@Override
	public void onUpdate() {
		if(mc.thePlayer.isCollidedHorizontally) {
			mc.thePlayer.motionY = 0.2;
			
			float var6 = 0.15f;
			
			if(mc.thePlayer.motionX < (double)-var6) {
				mc.thePlayer.motionX = (double)-var6;
			}
			if(mc.thePlayer.motionX < (double)-var6) {
				mc.thePlayer.motionX = (double)-var6;
			}
			if(mc.thePlayer.motionZ < (double)-var6) {
				mc.thePlayer.motionZ = (double)-var6;
			}
			if(mc.thePlayer.motionZ < (double)-var6) {
				mc.thePlayer.motionZ = (double)-var6;
			}
			mc.thePlayer.fallDistance = 0;
			
			if(mc.thePlayer.motionY < 0.15D) {
				mc.thePlayer.motionY = -0.15D;
			}
		}
	}
	
}
