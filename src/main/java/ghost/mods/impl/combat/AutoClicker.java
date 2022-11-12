package ghost.mods.impl.combat;

import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;
import ghost.mods.essential.settings.NumberSetting;
import ghost.utils.Timer;
import net.lax1dude.eaglercraft.EaglercraftRandom;

public class AutoClicker extends Mod {
	
	public Timer clickTimer = new Timer();
	public NumberSetting minCps = new NumberSetting("Minimum CPS", "The minimum CPS you want to autoclick.", 13,1,20,1,1);
	public NumberSetting maxCps = new NumberSetting("Maximum CPS", "The maximum CPS you want to autoclick.", 15,1,20,1,1);
	
	public AutoClicker() {
		super("Auto-Clicker", "Highly Customizable auto-clicker", Category.COMBAT);
		addSetting(minCps,maxCps);
	}
	
	
	
	@Override
	public void onEnable() {
		clickTimer.reset();
		super.onEnable();
	}
	
	@Override
	public void onUpdate() {
		EaglercraftRandom rand = new EaglercraftRandom();
		int min = (int) minCps.getValue();
		int max = (int) maxCps.getValue();
		int cps = (rand.nextInt(max+1 - min) + min);
		if(clickTimer.hasTimeElapsed(1000/cps, true)) {
			mc.click(0);
			mc.gameSettings.keyBindAttack.setKeyBindState(mc.gameSettings.keyBindAttack.keyCode, true);
			new Thread() {
				public void run() {
					try {
						Thread.sleep(1000/cps);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					mc.gameSettings.keyBindAttack.setKeyBindState(mc.gameSettings.keyBindAttack.keyCode, false);
				}
			}.start();
		}
		super.onUpdate();
	}

}
