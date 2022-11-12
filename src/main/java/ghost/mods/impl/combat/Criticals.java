package ghost.mods.impl.combat;

import ghost.events.Event;
import ghost.events.impl.EventAttack;
import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;
import ghost.mods.essential.settings.ModeSetting;

public class Criticals extends Mod {

	public Criticals() {
		super("Criticals","Deal critical damage everytime you hit.", Category.COMBAT, true);
		addSetting(mode);
	}
	
	public ModeSetting mode = new ModeSetting("Criticals", "", "Packet", "Minijump");
	
	@Override
	public void onEvent(Event e) {
		if(e instanceof EventAttack) {
			if(mc.thePlayer.isCollidedVertically) {
				if (mode.getValue() == "Packet") {
					/*
		            Minecraft.thePlayersendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.0625D, Minecraft.thePlayer.posZ, false));
		            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, false));
		            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.001D, Minecraft.thePlayer.posZ, false));
		            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, false));*/
		         } else if (mode.getValue() == "Minijump") {
		        	 mc.thePlayer.onGround = false;
		        	 mc.thePlayer.motionY = 0.1d;
		        	 mc.thePlayer.fallDistance = 0.1f;
		         }
			}
		}
		super.onEvent(e);
	}

}
