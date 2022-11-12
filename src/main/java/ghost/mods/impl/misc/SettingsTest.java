package ghost.mods.impl.misc;

import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;
import ghost.mods.essential.settings.BooleanSetting;
import ghost.mods.essential.settings.NumberSetting;
import ghost.utils.notifications.NotifType;
import ghost.utils.notifications.NotifUtils;

public class SettingsTest extends Mod {
	
	public NumberSetting numsetting = new NumberSetting("numbersetting", "testing valuesystem", 1.0f, 0.1f, 2.0f, 0.1f, 0.1f);
	public BooleanSetting boolsetting = new BooleanSetting("booleansetting", "testing valuesystem", false);
	
	public SettingsTest() {
		super("SettingsTest", "SettingsTest", Category.MISC);
		addSetting(numsetting, boolsetting);
	}
	
	@Override
	public void onEnable() {
		NotifUtils.addNotification("SettingsTest values", "numsetting: " + this.numsetting.getValue() + "boolsetting: " + this.boolsetting.getValue(), 8000, NotifType.INFO);
        mc.thePlayer.addChatMessage("numsetting: " + this.numsetting.getValue() + " boolsetting: " + this.boolsetting.getValue());
	}

}
