package ghost.mods.impl.render;

import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;
import ghost.mods.essential.settings.NumberSetting;

public class ESP extends Mod {

	public NumberSetting red = new NumberSetting("Red Value: ").withDescription("The color of the red value.").withMin(0).withMax(255).withIncrement(1).withValue(255);
	public NumberSetting green = new NumberSetting("Green Value: ").withDescription("The color of the green value.").withMin(0).withMax(255).withIncrement(1).withValue(0);
	public NumberSetting blue = new NumberSetting("Blue Value: ").withDescription("The color of the blue value.").withMin(0).withMax(255).withIncrement(1).withValue(0);
	
	public ESP() {
		super("ESP", "Extrasensory perception.", Category.RENDER);
		this.addSetting(red,green,blue);
	}

}
