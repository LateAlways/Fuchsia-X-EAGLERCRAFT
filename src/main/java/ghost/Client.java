package ghost;


import org.json.JSONArray;

import ghost.gui.AltManager;
import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;
import ghost.mods.essential.ModManager;
import ghost.mods.essential.RenderModule;
import ghost.mods.essential.settings.BooleanSetting;
import ghost.mods.essential.settings.ModeSetting;
import ghost.mods.essential.settings.NumberSetting;
import ghost.mods.essential.settings.Setting;
import ghost.utils.Alt;
import ghost.utils.XrayUtils;
import ghost.utils.notifications.NotifType;
import ghost.utils.notifications.NotifUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.src.NBTTagCompound;

public class Client {
	
	public String name = "Ghost";
	public String version = "";
	public static Client INSTANCE;
	public ModManager modManager;
	public int primaryColor = 0xFFf06292;
	public int secondaryColor = 0xFF9440bf;
	public AltManager altManager;
	
	static {
		INSTANCE = new Client();
	}
	
	public void saveSettings(NBTTagCompound yee) {
		boolean done = false;
		int alt = 0;
		while(!done) {
			String user = null;
			String pass = null;
			if(yee.hasKey("Alt"+alt+"_USERNAME"))
				user = yee.getString("Alt"+alt+"_USERNAME");
			if(yee.hasKey("Alt"+alt+"_PASSWORD"))
				pass = yee.getString("Alt"+alt+"_PASSWORD");
			if(user != null && pass != null) {
				yee.removeTag("Alt"+alt+"_USERNAME");
				yee.removeTag("Alt"+alt+"_PASSWORD");
			} else {
				done = true;
			}
			alt++;
		}
		alt = 0;
		for(Alt a : altManager.alts) {
			yee.setString("Alt"+alt+"_USERNAME", a.username);
			yee.setString("Alt"+alt+"_PASSWORD", a.password);
			alt++;
		}
		for(Mod m : modManager.modules) {
			yee.setInteger(m.name+"_keyCode", m.keyCode.keyCode);
			yee.setBoolean(m.name + "_isEnabled", m.enabled);
			if(m instanceof RenderModule) {
				yee.setBoolean(m.name+"_IsRenderModule", true);
				yee.setInteger(m.name+"_RENDER_X", ((RenderModule) m).x);
				yee.setInteger(m.name+"_RENDER_Y", ((RenderModule) m).y);
				yee.setInteger(m.name+"_RENDER_WIDTH", ((RenderModule) m).width);
				yee.setInteger(m.name+"_RENDER_HEIGHT", ((RenderModule) m).height);
			}
			for(Setting s : m.settings) {
				if(s instanceof NumberSetting) {
					yee.setFloat(m.name + "_NumberSetting_" + s.name, ((NumberSetting) s).getValue());
				}
				if(s instanceof ModeSetting) {
					yee.setString(m.name + "_ModeSetting_" + s.name, ((ModeSetting) s).getValue());
				}
				if(s instanceof BooleanSetting) {
					yee.setBoolean(m.name + "_BoolSetting_" + s.name, ((BooleanSetting) s).getValue());
				}
			}
		}
		for(Category c : Category.values()) {
			yee.setInteger(c.name+"_x", c.x);
			yee.setInteger(c.name+"_y", c.y);
			yee.setInteger(c.name+"_lastX", c.lastX);
			yee.setInteger(c.name+"_lastY", c.lastY);
			yee.setInteger(c.name+"_maxWidth", c.maxWidth);
			yee.setBoolean(c.name+"_shown", c.shown);
			yee.setBoolean(c.name+"_collapsed", c.collapsed);
			
		}
	}
	
	public void loadSettings(NBTTagCompound yee) {
		altManager = new AltManager();
		boolean done = false;
		int alt = 0;
		while(!done) {
			String user = null;
			String pass = null;
			if(yee.hasKey("Alt"+alt+"_USERNAME"))
				user = yee.getString("Alt"+alt+"_USERNAME");
			if(yee.hasKey("Alt"+alt+"_PASSWORD"))
				pass = yee.getString("Alt"+alt+"_PASSWORD");
			if(user != null && pass != null) {
				altManager.alts.add(new Alt(user,pass));
			} else {
				done = true;
			}
			alt++;
		}
		
		modManager = new ModManager();
		for(Mod m : modManager.modules) {
			if(yee.hasKey(m.name+"_keyCode")) m.keyCode.keyCode = yee.getInteger(m.name+"_keyCode");
			if(yee.hasKey(m.name+"_isEnabled")) m.enabled = yee.getBoolean(m.name+"_isEnabled");
			if(yee.hasKey(m.name+"_IsRenderModule")) {
				if(yee.hasKey(m.name+"_RENDER_X")) ((RenderModule) m).x = yee.getInteger(m.name+"_RENDER_X");
				if(yee.hasKey(m.name+"_RENDER_Y")) ((RenderModule) m).y = yee.getInteger(m.name+"_RENDER_Y");
				if(yee.hasKey(m.name+"_RENDER_WIDTH")) ((RenderModule) m).width = yee.getInteger(m.name+"_RENDER_WIDTH");
				if(yee.hasKey(m.name+"_RENDER_HEIGHT")) ((RenderModule) m).height = yee.getInteger(m.name+"_RENDER_HEIGHT");
			}
			for(Setting s : m.settings) {
				if(s instanceof NumberSetting) {
					if(yee.hasKey(m.name + "_NumberSetting_" + s.name)) ((NumberSetting) s).setValue(yee.getFloat(m.name + "_NumberSetting_" + s.name));
				}
				if(s instanceof ModeSetting) {
					if(yee.hasKey(m.name + "_ModeSetting_" + s.name)) ((ModeSetting) s).setValue(yee.getString(m.name + "_ModeSetting_" + s.name));
				}
				if(s instanceof BooleanSetting) {
					if(yee.hasKey(m.name + "_BoolSetting_" + s.name)) ((BooleanSetting) s).setValue(yee.getBoolean(m.name + "_BoolSetting_" + s.name));
				}
			}
		}
		for(Category c : Category.values()) {
			if(yee.hasKey(c.name+"_x")) c.x = yee.getInteger(c.name+"_x");
			if(yee.hasKey(c.name+"_y")) c.y = yee.getInteger(c.name+"_y");
			if(yee.hasKey(c.name+"_lastX")) c.lastX = yee.getInteger(c.name+"_lastX");
			if(yee.hasKey(c.name+"_lastY")) c.lastY = yee.getInteger(c.name+"_lastY");
			if(yee.hasKey(c.name+"_shown")) c.shown = yee.getBoolean(c.name+"_shown");
			if(yee.hasKey(c.name+"_collapsed")) c.collapsed = yee.getBoolean(c.name+"_collapsed");
		}
		NotifUtils.addNotification("Client loaded.", "All modules were loaded.", 8000, NotifType.INFO);
		XrayUtils.initXRayBlocks();
	}
	
	public void startup() {
		
	}
	
	public void postStartup() {
		//XrayUtils.initXRayBlocks();
	}

}
