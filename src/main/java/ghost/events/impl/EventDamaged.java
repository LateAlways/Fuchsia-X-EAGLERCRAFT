package ghost.events.impl;

import ghost.events.Event;
import net.minecraft.src.DamageSource;

public class EventDamaged extends Event {
	public DamageSource dmgSrc;
	
	public EventDamaged setDmgSrc(DamageSource d) {
		this.dmgSrc = d;
		return this;
	}
	
}
