package ghost.mods.impl.misc;

import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;
import ghost.mods.essential.settings.NumberSetting;

public class PingSpoofer extends Mod {

	public NumberSetting pingspoof = new NumberSetting("Ping", "", 300, 1, 10000, 1f, 1f);
	
	public PingSpoofer() {
		super("Ping Spoofer", "Tells the server a different ping from what you actually have.", Category.MISC);
		addSetting(pingspoof);
	}
	
	public float getPing() {
		return mc.theWorld != null ? pingspoof.getValue() : -1;
	}

}
