package ghost.mods.impl.misc;

import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;
import ghost.utils.Timer;
import net.lax1dude.eaglercraft.EaglercraftRandom;

public class ServerCrashing extends Mod {

	public ServerCrashing() {
		super("ServerCrasher", "how tf did you access this mod", Category.MISC, true);
	}
	
	public Timer timer = new Timer();
	
	@Override
	public void onUpdate() {
		int height = 270;
		int speed = 2;
		if(mc.thePlayer.isDead) {
			mc.thePlayer.respawnPlayer();
		}
		EaglercraftRandom rand = new EaglercraftRandom();
		rand.setSeed(System.currentTimeMillis());
		if(timer.hasTimeElapsed(5000, true)) {
			mc.thePlayer.sendChatMessage("yall bout to say the server laggy af right? ("+String.valueOf(rand.nextInt(100000))+")");
		}
		if(mc.thePlayer.posY < height) {
			mc.thePlayer.motionY = speed;
		}
		if(mc.thePlayer.posY > height) {
			mc.thePlayer.motionY = -speed;
		}
		if(mc.thePlayer.posY > height-3 && mc.thePlayer.posY < height+3) {
			mc.thePlayer.motionX = -speed;
		}
		super.onUpdate();
	}
	
	

}
