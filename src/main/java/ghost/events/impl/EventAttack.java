package ghost.events.impl;

import ghost.events.Event;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;

public class EventAttack extends Event {
	public Entity entity;
	public EventAttack(Entity par2Entity) {
		this.entity = par2Entity;
	}
}
