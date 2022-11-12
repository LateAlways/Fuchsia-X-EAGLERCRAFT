package ghost.utils;

import net.minecraft.src.DamageSource;

public class Hit {
	
	public Timer elapsed = new Timer();
	public boolean isPlr = false;
	
	public Hit(boolean isPlr) {
		this.isPlr = isPlr;
	}
	
}
