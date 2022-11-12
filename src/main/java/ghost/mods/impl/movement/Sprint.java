package ghost.mods.impl.movement;

import ghost.events.Event;
import ghost.events.impl.IsPlayerInWaterEvent;
import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;
import ghost.mods.essential.settings.BooleanSetting;
import ghost.utils.KeyboardUtils;
import ghost.utils.Timer;
import ghost.utils.notifications.NotifType;
import ghost.utils.notifications.NotifUtils;
import net.lax1dude.eaglercraft.EaglerAdapter;
import net.minecraft.src.Entity;

public class Sprint extends Mod {

	public BooleanSetting multiDirect = new BooleanSetting("Multi-Direction", "", false);
	
	public Sprint() {
		super("Sprint", "Sets your sprinting to true.", Category.MOVEMENT);
		addSetting(multiDirect);
	}
	
	@Override
	public void onDisable() {
		mc.thePlayer.setSprinting(false);
		super.onDisable();
	}
	
	@Override
	public void onUpdate() {
		if(mc.thePlayer != null && !mc.thePlayer.isSprinting()) {
			if(multiDirect.getValue()) {
				mc.thePlayer.setSprinting(true);
			}

			else if (mc.thePlayer.movementInput.moveForward > 0 && !mc.thePlayer.isBlocking() && !mc.thePlayer.isUsingItem() && !mc.thePlayer.isOnLadder() && !mc.thePlayer.isEating() && !mc.thePlayer.isSneaking()) {
				mc.thePlayer.setSprinting(true);
			}
		}
		super.onUpdate();
	}
	
}
