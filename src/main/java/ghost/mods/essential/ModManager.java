package ghost.mods.essential;

import java.util.ArrayList;
import java.util.List;

import ghost.events.Event;
import ghost.events.impl.EventRender;
import ghost.events.impl.EventUpdate;
import ghost.gui.ClickGUI;
import ghost.gui.HUDConfigScreen;
import ghost.mods.impl.combat.*;
import ghost.mods.impl.misc.*;
import ghost.mods.impl.movement.*;
import ghost.mods.impl.player.FastPlace;
import ghost.mods.impl.player.InventoryCleaner;
import ghost.mods.impl.render.*;
import ghost.mods.impl.world.Scaffold;
import ghost.utils.KeyboardUtils;
import ghost.utils.ServerUtils;
import ghost.utils.notifications.NotifType;
import ghost.utils.notifications.NotifUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiChat;
import net.minecraft.src.Timer;

public class ModManager {
	
	public List<Mod> modules = new ArrayList<Mod>();
	public Minecraft mc = Minecraft.getMinecraft();
	
	public Sprint sprint;
	public AutoClicker autoclicker;
	public AutoBlock autoblock;
	public AimAssist aimassist;
	public Velocity velocity;
	public Reach reach;
	public Wtap wtap;
	public NoClickDelay noclickdelay;
	public Notification notification;
	public Fly fly;
	public SettingsTest settingstest;
	public Keystrokes keystrokes;
	public Speed speed;
	public Spider spider;
	public Step step;
	public AutoDisable autodisable;
	public NoRotate norotate;
	public NoHurtCam nohurtcam;
	public PingSpoofer pingspoofer;
	public KillAura killaura;
	public NoFall nofall;
	public ServerCrashing servercrash;
	public Xray xray;
	public FullBright fullbright;
	public FastPlace fastplace;
	public Scaffold scaffold;
	public NoSlowdown noslowdown;
	//public FlyPacket flypacket;
	public InventoryCleaner invcleaner;
	public AutoArmor autoarmor;
	public ghost.mods.impl.render.ArrayList arraylist;
	public ESP esp;
	public ChestStealer cheststealer;
	public Criticals criticals;
	
	public ModManager() {
		//modules.add(flypacket = new FlyPacket());
		modules.add(sprint = new Sprint());
		modules.add(autoclicker = new AutoClicker());
		modules.add(autoblock = new AutoBlock());
		modules.add(aimassist = new AimAssist());
		modules.add(velocity = new Velocity());
		modules.add(reach = new Reach());
		modules.add(wtap = new Wtap());
		modules.add(noclickdelay = new NoClickDelay());
		modules.add(notification = new Notification());
		modules.add(fly = new Fly());
		modules.add(keystrokes = new Keystrokes());
		modules.add(speed = new Speed());
		modules.add(spider = new Spider());
		modules.add(step = new Step());
		modules.add(autodisable = new AutoDisable());
		modules.add(norotate = new NoRotate());
		modules.add(nohurtcam = new NoHurtCam());
		/*modules.add(*/pingspoofer = new PingSpoofer()/*)*/;
		modules.add(killaura = new KillAura());
		modules.add(nofall = new NoFall());
		/*modules.add(*/servercrash = new ServerCrashing()/*)*/;
		modules.add(xray = new Xray());
		modules.add(fullbright = new FullBright());
		modules.add(fastplace = new FastPlace());
		modules.add(scaffold = new Scaffold());
		modules.add(noslowdown = new NoSlowdown());
		//modules.add(invcleaner = new InventoryCleaner());
		modules.add(autoarmor = new AutoArmor());
		modules.add(arraylist = new ghost.mods.impl.render.ArrayList());
		modules.add(esp = new ESP());
		modules.add(cheststealer = new ChestStealer());
		modules.add(criticals = new Criticals());
	}
	
	public void onEvent(Event e) {
		for(int i = 0; i < modules.size(); i++) {
			Mod m = modules.get(i);
			if(m.isEnabled() && mc.thePlayer != null) {
				m.onEvent(e);
				if(e instanceof EventUpdate) {
					m.onUpdate();
				}
			}
		}
	}
	
	public void onKey(int keycode) { 
		if(!(mc.currentScreen instanceof ClickGUI) && !(mc.currentScreen instanceof GuiChat) && keycode == KeyboardUtils.KEY_Y) { //KEY Y
			mc.displayGuiScreen(new ClickGUI());
		}
		for(int i = 0; i < modules.size(); i++) {
			Mod m = modules.get(i);
			if(keycode == m.keyCode.keyCode && keycode != 0) {
				m.toggle();
				if(this.notification.modtoggles.getValue()) {
					NotifUtils.addNotification("Module Toggled", "Module "+m.name+" has been "+(m.isEnabled() == true?"§aEnabled§r":"§4Disabled§r"), 500, NotifType.INFO);
				}
			}
		}
	}
	
	public List<Mod> getModulesByCategory(Category c) {
		List<Mod> modules1 = new ArrayList<Mod>();
		for(int i = 0; i < modules.size(); i++) {
			Mod m = modules.get(i);
			if(m.category == c) {
				modules1.add(m);
			}
		}
		return modules1;
	}
	
	public void renderMods() {
		NotifUtils.renderNotifications();
		onEvent(new EventRender());
		for(int i = 0; i < modules.size(); i++) {
			Mod m = modules.get(i);
			if(m.isEnabled() && (m instanceof RenderModule)) {
				if(!(mc.currentScreen instanceof HUDConfigScreen)) {
					((RenderModule) m).draw();
				}
			}
		}
	}

}
