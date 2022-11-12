package ghost.mods.impl.render;

import java.util.Comparator;
import java.util.List;

import ghost.Client;
import ghost.events.Event;
import ghost.events.impl.EventRender;
import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;
import ghost.utils.RenderUtils;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.MathHelper;

public class ArrayList extends Mod {

	public ArrayList() {
		super("ArrayList", "Shows the mods you have enabled", Category.RENDER);
		settings.clear();
	}
	
	@Override
	public void onEvent(Event e) {
		shown.setValue(false);
		if(e instanceof EventRender) {
			Client.INSTANCE.modManager.modules.sort(Comparator.comparingInt(m -> mc.fontRenderer.getStringWidth(((Mod)m).name)).reversed());
			List<Mod> enabledMods = new java.util.ArrayList<Mod>();
			
			for(int i = 0; i < Client.INSTANCE.modManager.modules.size(); i++) {
				if(Client.INSTANCE.modManager.modules.get(i).isEnabled() && Client.INSTANCE.modManager.modules.get(i).shown.getValue()) {
					enabledMods.add(Client.INSTANCE.modManager.modules.get(i));
				}
			}
			
			int count = 0;
			for(Mod m : enabledMods) {
				if(m.isEnabled()) {
					Gui.drawRect(GuiScreen.width-mc.fontRenderer.getStringWidth(m.name)-6, count*12, GuiScreen.width, count*12+12, 0x70000000);
					if(m.blatant)
					mc.fontRenderer.drawString("§6⚠§r", GuiScreen.width-mc.fontRenderer.getStringWidth(m.name), count*12+12/2-mc.fontRenderer.FONT_HEIGHT/2, -1);
					RenderUtils.renderChromaString(m.blatant ? m.name.replace("§6⚠§r","") : m.name, GuiScreen.width-mc.fontRenderer.getStringWidth(m.name)-2+(m.blatant?mc.fontRenderer.getStringWidth("§6⚠§r"):0), count*12+12/2-mc.fontRenderer.FONT_HEIGHT/2);
					RenderUtils.drawChromaRectangle(GuiScreen.width-mc.fontRenderer.getStringWidth(m.name)-7+1/2, count*12, GuiScreen.width-mc.fontRenderer.getStringWidth(m.name)-6, count*12+12);
					if(count+1 == enabledMods.size()) {
						RenderUtils.drawChromaRectangle(GuiScreen.width-mc.fontRenderer.getStringWidth(m.name)-7+1/2, count*12+12, GuiScreen.width, count*12+12+1);
						break;
					}
					RenderUtils.drawChromaRectangle(GuiScreen.width-mc.fontRenderer.getStringWidth(m.name)-7+1/2, count*12+12, GuiScreen.width-mc.fontRenderer.getStringWidth(enabledMods.get(count+1).name)-6, count*12+12+1);
					++count;
				}
			}
		}
		super.onEvent(e);
	}
}
