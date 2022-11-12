package ghost.mods.essential;

import java.util.ArrayList;
import java.util.List;

import ghost.events.Event;
import ghost.events.impl.EventUpdate;
import ghost.mods.essential.settings.BooleanSetting;
import ghost.mods.essential.settings.KeyCodeSetting;
import ghost.mods.essential.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;

public class Mod {
	
	public Minecraft mc = Minecraft.getMinecraft();
	public FontRenderer fr = mc.fontRenderer;
	
	public String name;
	public Category category;
	public String description;
	public KeyCodeSetting keyCode = new KeyCodeSetting("Keybind", "Set the keybind of the mod.", 0);
	public boolean enabled = false;
	public boolean debounceClickGUI = false;
	public boolean debounceClickGUI1 = false;
	
	public BooleanSetting shown = new BooleanSetting("Displayed", "", true);
	
	public boolean blatant = false;
	
	public List<Setting> settings = new ArrayList<Setting>();
	
	public Mod(String name,String description,Category cat) {
		this.name = name;
		this.category = cat;
		this.addSetting(shown);
		this.description = description;
	}
	
	public Mod(String name,String description,Category cat, boolean blatant) {
		if(blatant) {
			this.blatant = true;
			this.name = "§6⚠§r " + name;
		} else
			this.name = name;
		this.category = cat;
		this.description = description;
	}
	
	public void addSetting(Setting... settings) {
		for(Setting s : settings) {
			this.settings.add(s);
		}
		
	}
	
	public void onUpdate() {
		
	}
	
	public void onEvent(Event e) {
		
	}
	
	public void onEnable() {
		
	}
	
	public void onDisable() {
		
	}
	
	public void toggle() {
		this.enabled = !this.enabled;
		if(this.enabled) onEnable(); else onDisable();
	}
	
	public void setEnabled(boolean state) {
		this.enabled = state;
		if(this.enabled) onEnable(); else onDisable();
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public int getKeyCode() {
		return keyCode.getKeyCode();
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
}
