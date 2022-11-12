package ghost.mods.impl.combat;

import java.util.ArrayList;
import java.util.List;

import ghost.events.Event;
import ghost.events.impl.EventAttack;
import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;
import ghost.mods.essential.settings.NumberSetting;
import ghost.utils.Timer;
import net.lax1dude.eaglercraft.EaglerAdapter;

public class Wtap extends Mod {

	public NumberSetting delay = new NumberSetting("Delay","Delay to WTap after attacking in ms.", 5.0F, 1.0F, 50F,1.0F,1.0F);
	
	public Wtap() {
		super("WTap", "Reset your sprint perfectly.", Category.COMBAT);
		addSetting(delay);
	}
	
	public Timer timer = new Timer();
	public boolean attacked = false;
	
	@Override
	public void onEvent(Event e) {
		if(e instanceof EventAttack) {
			timer.reset();
			attacked = true;
		}
		if(timer.hasTimeElapsed((long) delay.getValue(), true) && attacked == true) {
			boolean wasSprinting = mc.thePlayer.isSprinting();
			if(wasSprinting) {
				boolean wasWalking = mc.gameSettings.keyBindForward.isPressed();
				if(wasWalking) {
					mc.gameSettings.keyBindForward.setKeyBindState(mc.gameSettings.keyBindForward.keyCode, false);
				}
				new Thread() {
					public void run() {
						try {
							Thread.sleep((long) delay.getValue());
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						mc.gameSettings.keyBindForward.setKeyBindState(mc.gameSettings.keyBindForward.keyCode, EaglerAdapter.isKeyDown(mc.gameSettings.keyBindForward.keyCode));
						if(wasSprinting && mc.thePlayer != null) {
							mc.thePlayer.setSprinting(true);
						}
					}
				}.start();
			}
			attacked = false;
		}
	}
	
}
