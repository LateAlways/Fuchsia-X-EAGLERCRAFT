package ghost.mods.impl.misc;

import ghost.Client;
import ghost.events.Event;
import ghost.events.impl.EventProcessPacket;
import ghost.events.impl.EventSetPosition;
import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;
import ghost.mods.essential.settings.BooleanSetting;
import ghost.mods.essential.settings.NumberSetting;
import ghost.utils.Timer;
import ghost.utils.notifications.NotifType;
import ghost.utils.notifications.NotifUtils;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet34EntityTeleport;
import net.minecraft.src.Packet35EntityHeadRotation;

public class AutoDisable extends Mod {

	public BooleanSetting autoenable = new BooleanSetting("Re-enable", "Re-enable mods after being disabled for some time.", true);
	public NumberSetting enableTime = new NumberSetting("Time to re-enable", "Time that AutoDisable should wait before re-enabling the mod. (seconds)", 5f, 1f, 30f, 0.5f,0.5f);
	
	public AutoDisable() {
		super("AutoDisable", "Automatically disables a mod when you get flagged from an anticheat.", Category.MISC);
	}
	
	public Timer speedLagbackTimer = new Timer();
	public int speedFlags = 0;
	
	public void onWinGame() {
		if(isEnabled()) {
			if(Client.INSTANCE.modManager.killaura.isEnabled()) {
				Client.INSTANCE.modManager.killaura.setEnabled(false);
				NotifUtils.addNotification("AutoDisable", "KillAura has been disabled because you won.", 3000, NotifType.WARNING);
			}
			if(Client.INSTANCE.modManager.cheststealer.isEnabled()) {
				Client.INSTANCE.modManager.cheststealer.setEnabled(false);
				NotifUtils.addNotification("AutoDisable", "ChestStealer has been disabled because you won.", 3000, NotifType.WARNING);
			}
			if(Client.INSTANCE.modManager.autoarmor.isEnabled()) {
				Client.INSTANCE.modManager.autoarmor.setEnabled(false);
				NotifUtils.addNotification("AutoDisable", "AutoArmor has been disabled because you won.", 3000, NotifType.WARNING);
			}
			if(Client.INSTANCE.modManager.invcleaner.isEnabled()) {
				Client.INSTANCE.modManager.invcleaner.setEnabled(false);
				NotifUtils.addNotification("AutoDisable", "Inventory Cleaner has been disabled because you won.", 3000, NotifType.WARNING);
			}
			if(Client.INSTANCE.modManager.speed.isEnabled()) {
				Client.INSTANCE.modManager.speed.setEnabled(false);
				NotifUtils.addNotification("AutoDisable", "Speed has been disabled because you won.", 3000, NotifType.WARNING);
			}
		}
	}
	
	@Override
	public void onEvent(Event e) {
		if(e instanceof EventSetPosition) {
			
			/*if(Client.INSTANCE.modManager.speed.isEnabled()) {
				if(speedLagbackTimer.hasTimeElapsed(1000, false)) {
					speedFlags = MathHelper.clamp_int(speedFlags-1, 0, 999999999);
				} else {
					speedFlags++;
					if(speedFlags >= 3) {
						Client.INSTANCE.modManager.speed.enabled = false;
						NotifUtils.addNotification("AutoDisable", "Speed flagged over 3 times. Disabled for "+enableTime.getValue()+" secs.", 3000, NotifType.WARNING);
						if(autoenable.getValue()) {
							new Thread() {
								public void run() {
									try {
										sleep((long)(enableTime.getValue()*1000));
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									NotifUtils.addNotification("AutoDisable", "Re-enabled speed.", 1500, NotifType.INFO);
									Client.INSTANCE.modManager.speed.enabled = true;
								}
							}.start();
						}
					}
				}
			}*/
		}
		super.onEvent(e);
	}

}
