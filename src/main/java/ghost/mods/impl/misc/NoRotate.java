package ghost.mods.impl.misc;

import ghost.events.Event;
import ghost.events.impl.EventSetPosition;
import ghost.events.impl.EventSetPositionAndRotation;
import ghost.events.impl.EventSetRotation;
import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;

public class NoRotate extends Mod {

	public NoRotate() {
		super("NoRotate", "Prevents the server from changing your head rotation.", Category.MISC);
	}
	
	@Override
	public void onEvent(Event e) {
		super.onEvent(e);
	}
}
