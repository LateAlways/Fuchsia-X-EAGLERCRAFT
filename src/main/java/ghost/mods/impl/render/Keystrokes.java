package ghost.mods.impl.render;

import ghost.mods.essential.Category;
import ghost.mods.essential.RenderModule;
import ghost.utils.KeyboardUtils;
import net.lax1dude.eaglercraft.EaglerAdapter;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Gui;

public class Keystrokes extends RenderModule {

	public Keystrokes() {
		super("Keystrokes", "Shows the keys pressed on your keyboard", Category.RENDER, 10, 10, 100, 300);
	}
	
	@Override
	public void draw() {
 		Gui.drawRect(this.x+25+5, this.y, this.x+25+5+25, this.y+25, !mc.gameSettings.keyBindForward.pressed ? 0x88101010 : 0x88888888);
		
		Gui.drawRect(this.x+25+5, this.y+5+25, this.x+25+5+25, this.y+25+5+25, !mc.gameSettings.keyBindBack.pressed ? 0x88101010 : 0x88888888);
		Gui.drawRect(this.x+25+5+5+25, this.y+5+25, this.x+25+5+5+25+25, this.y+25+5+25, !mc.gameSettings.keyBindRight.pressed ? 0x88101010 : 0x88888888);
		Gui.drawRect(this.x, this.y+5+25, this.x+25, this.y+25+5+25, !mc.gameSettings.keyBindLeft.pressed ? 0x88101010 : 0x88888888);
		
		Gui.drawRect(this.x, this.y+5+25+5+25, this.x+40, this.y+25+5+25+5+25, !mc.gameSettings.keyBindAttack.pressed ? 0x88101010 : 0x88888888);
		Gui.drawRect(this.x+40+5, this.y+5+25+5+25, this.x+40+40+5, this.y+25+5+25+5+25, !mc.gameSettings.keyBindUseItem.pressed ? 0x88101010 : 0x88888888);
		
		Minecraft.getMinecraft().fontRenderer.drawString(Minecraft.getMinecraft().gameSettings.getKeyDisplayString(Minecraft.getMinecraft().gameSettings.keyBindForward.keyCode), this.x+25+5+(25/2-mc.fontRenderer.getStringWidth(Minecraft.getMinecraft().gameSettings.getKeyDisplayString(Minecraft.getMinecraft().gameSettings.keyBindForward.keyCode))/2), this.y+25/2-mc.fontRenderer.FONT_HEIGHT/2, mc.gameSettings.keyBindForward.pressed ? 0 : -1);
		Minecraft.getMinecraft().fontRenderer.drawString(Minecraft.getMinecraft().gameSettings.getKeyDisplayString(Minecraft.getMinecraft().gameSettings.keyBindLeft.keyCode), this.x+(25/2-mc.fontRenderer.getStringWidth(Minecraft.getMinecraft().gameSettings.getKeyDisplayString(Minecraft.getMinecraft().gameSettings.keyBindLeft.keyCode))/2), (this.y+25+(25/2)), mc.gameSettings.keyBindLeft.pressed ? 0 : -1);
		Minecraft.getMinecraft().fontRenderer.drawString(Minecraft.getMinecraft().gameSettings.getKeyDisplayString(Minecraft.getMinecraft().gameSettings.keyBindBack.keyCode), this.x+25+5+(25/2-mc.fontRenderer.getStringWidth(Minecraft.getMinecraft().gameSettings.getKeyDisplayString(Minecraft.getMinecraft().gameSettings.keyBindBack.keyCode))/2), (this.y+25+(25/2)), mc.gameSettings.keyBindBack.pressed ? 0 : -1);
		Minecraft.getMinecraft().fontRenderer.drawString(Minecraft.getMinecraft().gameSettings.getKeyDisplayString(Minecraft.getMinecraft().gameSettings.keyBindRight.keyCode), this.x+25+25+5+5+(25/2-mc.fontRenderer.getStringWidth(Minecraft.getMinecraft().gameSettings.getKeyDisplayString(Minecraft.getMinecraft().gameSettings.keyBindRight.keyCode))/2), (this.y+25+(25/2)), mc.gameSettings.keyBindRight.pressed ? 0 : -1);
		Minecraft.getMinecraft().fontRenderer.drawString("LEFT", this.x+40/2-mc.fontRenderer.getStringWidth("LEFT")/2, (this.y+25+25+5+5+25/2)-mc.fontRenderer.FONT_HEIGHT/2, mc.gameSettings.keyBindAttack.pressed ? 0 : -1);
		Minecraft.getMinecraft().fontRenderer.drawString("RIGHT", this.x+40+5+40/2-mc.fontRenderer.getStringWidth("RIGHT")/2, (this.y+25+25+5+5+25/2)-mc.fontRenderer.FONT_HEIGHT/2, mc.gameSettings.keyBindUseItem.pressed ? 0 : -1);
		this.setHeight(25+5+25+5+25);
		this.setWidth(25+5+25+5+25);
		super.draw();
	}

}
