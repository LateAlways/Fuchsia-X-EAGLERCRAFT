package ghost.gui;

import ghost.Client;
import ghost.mods.essential.Mod;
import ghost.mods.essential.RenderModule;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;

public class HUDConfigScreen extends GuiScreen {

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		for(Mod m : Client.INSTANCE.modManager.modules) {
			if(m.isEnabled() && (m instanceof RenderModule)) {
				((RenderModule) m).renderLayout(par1, par2);
			}
		}
		for(Object b : this.buttonList) {
			if(b instanceof GuiButton) {
				Gui.drawRect(((GuiButton) b).xPosition, ((GuiButton) b).yPosition, ((GuiButton) b).xPosition+((GuiButton) b).width, ((GuiButton) b).yPosition+((GuiButton) b).height, 0xFF000000);
				mc.fontRenderer.drawString(((GuiButton) b).displayString,((GuiButton) b).xPosition+((GuiButton) b).width/2-mc.fontRenderer.getStringWidth(((GuiButton) b).displayString)/2,((GuiButton) b).yPosition+((GuiButton) b).height/2-mc.fontRenderer.FONT_HEIGHT/2, -1);
			}
		}
	}
	
	@Override
	public void initGui() {
		this.buttonList.add(new GuiButton(1, this.width / 2 - 50, this.height / 2 - 15, 100, 30, "Back"));
		super.initGui();
	}
	
	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if(par1GuiButton.id == 1) {
			mc.displayGuiScreen(new ClickGUI());
		}
		super.actionPerformed(par1GuiButton);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
}
