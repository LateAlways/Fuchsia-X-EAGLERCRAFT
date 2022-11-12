package ghost.mods.impl.movement;

import ghost.events.Event;
import ghost.events.impl.EventProcessPacket;
import ghost.events.impl.EventSetPosition;
import ghost.events.impl.EventSetPositionAndRotation;
import ghost.events.impl.EventVelocity;
import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;
import ghost.mods.essential.settings.NumberSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Potion;
import net.minecraft.src.Timer;

public class Speed extends Mod {
	
	public NumberSetting multiplier = new NumberSetting("Multiplier", "", 25f,5f,100f,1f,1f);
	
	public Speed() {
		super("Speed","Modify the walkspeed.",Category.MOVEMENT,true);
		addSetting(multiplier);
	}
	
	public ghost.utils.Timer flagTimer = new ghost.utils.Timer();
	
	public double lastMotionX = 0;
	public double lastMotionZ = 0;
	
	public boolean cancelPackets = false;
	
	@Override
	public void onEnable() {
		flagTimer.reset();
		Minecraft.getMinecraft().timer.timerSpeed = 1+multiplier.getValue()/100;
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		flagTimer.reset();
		Minecraft.getMinecraft().timer.timerSpeed = 1;
		super.onDisable();
	}
	
	@Override
	public void onUpdate() {
		if(mc.thePlayer.movementInput.moveForward > 0 || mc.thePlayer.movementInput.moveStrafe > 0) {
			if(mc.thePlayer.onGround) {
				cancelPackets = true;
				mc.thePlayer.motionY = 0.41999998688697815D/1;

				if (mc.thePlayer.isPotionActive(Potion.jump)) {
					mc.thePlayer.motionY += (double) ((float) (mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
				}

				if (mc.thePlayer.isSprinting()) {
					float var1 = mc.thePlayer.rotationYaw * 0.017453292F;
					mc.thePlayer.motionX -= (double) (MathHelper.sin(var1) * 0.2F);
					mc.thePlayer.motionZ += (double) (MathHelper.cos(var1) * 0.2F);
				}

				mc.thePlayer.isAirBorne = true;
				if (mc.thePlayer.isSprinting()) {
					mc.thePlayer.addExhaustion(0.8F/1F);
				} else {
					mc.thePlayer.addExhaustion(0.2F/1F);
				}
				cancelPackets = false;
				Timer.timerSpeed = 1+multiplier.getValue()/100;
			} else {
				cancelPackets = true;
			}
		}
	}
	
	@Override
	public void onEvent(Event e) {
		if(e instanceof EventSetPosition && cancelPackets) e.cancelled = true;
		if(e instanceof EventSetPositionAndRotation && cancelPackets) e.cancelled = true;
		if(e instanceof EventProcessPacket && cancelPackets) e.cancelled = true;
		super.onEvent(e);
	}
	
}
