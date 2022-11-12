package ghost.mods.impl.combat;

import ghost.events.Event;
import ghost.events.impl.EventAttack;
import ghost.events.impl.EventUseItem;
import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;
import ghost.mods.essential.settings.NumberSetting;
import ghost.utils.Timer;
import net.minecraft.src.ItemSword;
import net.minecraft.src.Packet15Place;

public class AutoBlock extends Mod {

	public NumberSetting delay = new NumberSetting("Delay","Delay to block after attacking in ms.", 15.0F, 1.0F, 50F,1.0F,1.0F);
	public NumberSetting blockDuration = new NumberSetting("Block Duration", "The time you want the block to last in ms.", 20f, 1.0f, 120f, 1.0f, 1.0f);
	
	public AutoBlock() {
		super("AutoBlock", "Reset sprint through blocking,", Category.COMBAT);
		addSetting(delay,blockDuration);
	}
	
	public boolean attacked = false;
	public Timer timer = new Timer();
	
	@Override
	public void onEvent(Event e) {
		if(e instanceof EventAttack) {
			if(mc.thePlayer.inventory.getCurrentItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
				timer.reset();
				attacked = true;
			}
		}
		if(timer.hasTimeElapsed((long) delay.getValue(), true) && attacked) {
			if(mc.thePlayer.inventory.getCurrentItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
				mc.thePlayer.inventory.getCurrentItem().useItemRightClick(mc.theWorld, mc.thePlayer);
				mc.playerController.syncCurrentPlayItem();
				mc.getNetHandler().addToSendQueue(new Packet15Place(-1, -1, -1, 255, mc.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
				new Thread() {
					public void run() {
						try {
							sleep((long) blockDuration.getValue());
						} catch (InterruptedException e) {}
						mc.thePlayer.stopUsingItem();
					};
				}.start();
				
			}
			attacked = false;
		}
	}
}
