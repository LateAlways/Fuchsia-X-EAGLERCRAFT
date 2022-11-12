package ghost.utils.notifications;

import java.util.ArrayList;
import java.util.List;

import ghost.Client;
import ghost.utils.RenderUtils;
import ghost.utils.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.MathHelper;

public class NotifUtils {
	
	static List<Notification> notifications = new ArrayList<Notification>();

	public static Timer timer1 = new Timer();
	
	public static void renderNotifications() {
		FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
		Minecraft mc = Minecraft.getMinecraft();
		int count = 0;
		if(notifications.size() >= 0) { 
			for(int i = notifications.toArray().length-1; i > 0; i--) {
				Notification n = notifications.get(i);
				if(Client.INSTANCE.modManager.notification.isEnabled()) {
					if(n.notifType == NotifType.INFO) {
						RenderUtils.drawRoundedRect(GuiScreen.width-fr.getStringWidth(fr.getStringWidth(n.name) > fr.getStringWidth(n.description) ? n.name : n.description)-50+(int)n.xAdd, GuiScreen.height-80 - (60*(i-1))-60+(int)n.yAdd, fr.getStringWidth(fr.getStringWidth(n.name) > fr.getStringWidth(n.description) ? n.name : n.description)+50+3+(int)n.xAdd, 50, 2, 0xe6000000);
						float xPositionBar = (float)(System.currentTimeMillis()-n.timer.lastMs)/n.ms;
						if(n.yAdd > 0 && n.xAdd > 0 && !n.timer.hasTimeElapsed(n.ms, false) && n.animTimer.hasTimeElapsed(2, false)) {
							n.yAdd -= 0.35*(System.currentTimeMillis()-n.animTimer.lastMs);
							n.xAdd -= 0.35*(System.currentTimeMillis()-n.animTimer.lastMs);
							n.xAdd = MathHelper.clamp_float(n.xAdd, 0, 10);
							n.yAdd = MathHelper.clamp_float(n.yAdd, 0, 10);
							n.animTimer.reset();
						}
						if(!n.timer.hasTimeElapsed(n.ms, false)) {
							n.animTimer1.reset();
						}
						if(n.timer.hasTimeElapsed(n.ms, false) && GuiScreen.width-fr.getStringWidth(fr.getStringWidth(n.name) > fr.getStringWidth(n.description) ? n.name : n.description)-50+n.xAdd >= GuiScreen.width) {
							//addNotification(n.name, n.description, n.ms, n.notifType);
							notifications.remove(i);
						} else if(n.timer.hasTimeElapsed(n.ms, false) && n.animTimer1.hasTimeElapsed(2, false)) {
							n.xAdd += 0.35*(System.currentTimeMillis()-n.animTimer1.lastMs);
							n.animTimer1.reset();
						}
						Gui.drawRect(GuiScreen.width-(int)((1-xPositionBar)*(fr.getStringWidth(fr.getStringWidth(n.name) > fr.getStringWidth(n.description) ? n.name : n.description)+50))+(int)n.xAdd, GuiScreen.height-30 - (60*(i-1))-60+(int)n.yAdd, GuiScreen.width+(int)n.xAdd, GuiScreen.height-29 - (60*(i-1))-60+(int)n.yAdd, -1);
						fr.drawString(n.name, GuiScreen.width-fr.getStringWidth(fr.getStringWidth(n.name) > fr.getStringWidth(n.description) ? n.name : n.description)-10+(int)n.xAdd, GuiScreen.height-70 - (60*(i-1))-60+(int)n.yAdd, -1);
						fr.drawString(n.description, GuiScreen.width-fr.getStringWidth(fr.getStringWidth(n.name) > fr.getStringWidth(n.description) ? n.name : n.description)-10+(int)n.xAdd, GuiScreen.height-70+fr.FONT_HEIGHT+5 - (60*(i-1))-60+(int)n.yAdd, 0xFF9f9f9f);
						fr.drawString("§lINFO§r", (int)(GuiScreen.width-fr.getStringWidth(fr.getStringWidth(n.name) > fr.getStringWidth(n.description) ? n.name : n.description)-41+n.xAdd), (int)(GuiScreen.height-60 - (60*(i-1))-60+n.yAdd), -1);
					} else if(n.notifType == NotifType.ERROR) {
						RenderUtils.drawRoundedRect(GuiScreen.width-fr.getStringWidth(fr.getStringWidth(n.name) > fr.getStringWidth(n.description) ? n.name : n.description)-60+(int)n.xAdd, GuiScreen.height-80 - (60*(i-1))-60+(int)n.yAdd, fr.getStringWidth(fr.getStringWidth(n.name) > fr.getStringWidth(n.description) ? n.name : n.description)+50+3+(int)n.xAdd+10, 50, 2, 0x50ff0000);
						float xPositionBar = (float)(System.currentTimeMillis()-n.timer.lastMs)/n.ms;
						if(n.yAdd > 0 && n.xAdd > 0 && !n.timer.hasTimeElapsed(n.ms, false) && n.animTimer.hasTimeElapsed(2, false)) {
							n.yAdd -= 0.35*(System.currentTimeMillis()-n.animTimer.lastMs);
							n.xAdd -= 0.35*(System.currentTimeMillis()-n.animTimer.lastMs);
							n.animTimer.reset();
						}
						if(!n.timer.hasTimeElapsed(n.ms, false)) {
							n.animTimer1.reset();
						}
						if(n.timer.hasTimeElapsed(n.ms, false) && GuiScreen.width-fr.getStringWidth(fr.getStringWidth(n.name) > fr.getStringWidth(n.description) ? n.name : n.description)-60+n.xAdd >= GuiScreen.width) {
							//addNotification(n.name, n.description, n.ms, n.notifType);
							notifications.remove(i);
						} else if(n.timer.hasTimeElapsed(n.ms, false) && n.animTimer1.hasTimeElapsed(2, false)) {
							n.xAdd += 0.35*(System.currentTimeMillis()-n.animTimer1.lastMs);
							n.animTimer1.reset();
						}
						Gui.drawRect(GuiScreen.width-(int)((1-xPositionBar)*(fr.getStringWidth(fr.getStringWidth(n.name) > fr.getStringWidth(n.description) ? n.name : n.description)+60))+(int)n.xAdd, GuiScreen.height-30 - (60*(i-1))-60+(int)n.yAdd, GuiScreen.width+(int)n.xAdd+10, GuiScreen.height-29 - (60*(i-1))-60+(int)n.yAdd, -1);
						fr.drawString(n.name, GuiScreen.width-fr.getStringWidth(fr.getStringWidth(n.name) > fr.getStringWidth(n.description) ? n.name : n.description)-10+(int)n.xAdd, GuiScreen.height-70 - (60*(i-1))-60+(int)n.yAdd, -1);
						fr.drawString(n.description, GuiScreen.width-fr.getStringWidth(fr.getStringWidth(n.name) > fr.getStringWidth(n.description) ? n.name : n.description)-10+(int)n.xAdd, GuiScreen.height-70+fr.FONT_HEIGHT+5 - (60*(i-1))-60+(int)n.yAdd, 0xFF9f9f9f);
						fr.drawString("§lERROR§r", (int)(GuiScreen.width-fr.getStringWidth(fr.getStringWidth(n.name) > fr.getStringWidth(n.description) ? n.name : n.description)-51+n.xAdd), (int)(GuiScreen.height-60 - (60*(i-1))-60+n.yAdd), 0xFFFF0000);
					} else if(n.notifType == NotifType.WARNING) {
						RenderUtils.drawRoundedRect(GuiScreen.width-fr.getStringWidth(fr.getStringWidth(n.name) > fr.getStringWidth(n.description) ? n.name : n.description)-70+(int)n.xAdd, GuiScreen.height-80 - (60*(i-1))-60+(int)n.yAdd, fr.getStringWidth(fr.getStringWidth(n.name) > fr.getStringWidth(n.description) ? n.name : n.description)+50+3+(int)n.xAdd+20, 50, 2, 0x50ffe664);
						float xPositionBar = (float)(System.currentTimeMillis()-n.timer.lastMs)/n.ms;
						if(n.yAdd > 0 && n.xAdd > 0 && !n.timer.hasTimeElapsed(n.ms, false) && n.animTimer.hasTimeElapsed(2, false)) {
							n.yAdd -= 0.35*(System.currentTimeMillis()-n.animTimer.lastMs);
							n.xAdd -= 0.35*(System.currentTimeMillis()-n.animTimer.lastMs);
							n.animTimer.reset();
						}
						if(!n.timer.hasTimeElapsed(n.ms, false)) {
							n.animTimer1.reset();
						}
						if(n.timer.hasTimeElapsed(n.ms, false) && GuiScreen.width-fr.getStringWidth(fr.getStringWidth(n.name) > fr.getStringWidth(n.description) ? n.name : n.description)-60+n.xAdd >= GuiScreen.width) {
							//addNotification(n.name, n.description, n.ms, n.notifType);
							notifications.remove(i);
						} else if(n.timer.hasTimeElapsed(n.ms, false) && n.animTimer1.hasTimeElapsed(2, false)) {
							n.xAdd += 0.35*(System.currentTimeMillis()-n.animTimer1.lastMs);
							n.animTimer1.reset();
						}
						Gui.drawRect(GuiScreen.width-(int)((1-xPositionBar)*(fr.getStringWidth(fr.getStringWidth(n.name) > fr.getStringWidth(n.description) ? n.name : n.description)+70))+(int)n.xAdd, GuiScreen.height-30 - (60*(i-1))-60+(int)n.yAdd, GuiScreen.width+(int)n.xAdd+20, GuiScreen.height-29 - (60*(i-1))-60+(int)n.yAdd, -1);
						fr.drawString(n.name, GuiScreen.width-fr.getStringWidth(fr.getStringWidth(n.name) > fr.getStringWidth(n.description) ? n.name : n.description)-10+(int)n.xAdd, GuiScreen.height-70 - (60*(i-1))-60+(int)n.yAdd, -1);
						fr.drawString(n.description, GuiScreen.width-fr.getStringWidth(fr.getStringWidth(n.name) > fr.getStringWidth(n.description) ? n.name : n.description)-10+(int)n.xAdd, GuiScreen.height-70+fr.FONT_HEIGHT+5 - (60*(i-1))-60+(int)n.yAdd, 0xFF9f9f9f);
						fr.drawString("§lWARNING§r", (int)(GuiScreen.width-fr.getStringWidth(fr.getStringWidth(n.name) > fr.getStringWidth(n.description) ? n.name : n.description)-61+n.xAdd), (int)(GuiScreen.height-60 - (60*(i-1))-60+n.yAdd), 0xFFffe664);
					}
				}
			}
		}
	}
	
	public static void addNotification(String name, String description, long ms, NotifType notifType) {
		notifications.add(new Notification(name,description,ms,notifType));
	}
	
}
