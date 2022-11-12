package ghost.events.impl;

import ghost.events.Event;
import net.minecraft.src.Entity;

public class IsPlayerInWaterEvent extends Event {
	
	public boolean inWater = false;
	public Entity entity;
	
	public IsPlayerInWaterEvent(boolean inWater, Entity entity) {
		this.inWater = inWater;
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public boolean isInWater() {
		return inWater;
	}

	public void setInWater(boolean inWater) {
		this.inWater = inWater;
	}
	
}
