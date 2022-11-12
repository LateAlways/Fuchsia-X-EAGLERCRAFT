package ghost.gui;

import java.awt.Color;

import ghost.Client;
import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;
import ghost.mods.essential.settings.BooleanSetting;
import ghost.mods.essential.settings.ModeSetting;
import ghost.mods.essential.settings.NumberSetting;
import ghost.mods.essential.settings.Setting;
import ghost.utils.KeyboardUtils;
import ghost.utils.RenderUtils;
import net.lax1dude.eaglercraft.EaglerAdapter;
import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.MathHelper;

public class ClickGUI extends GuiScreen {
	
	public boolean isMouseInside(int mouseX, int mouseY, int x, int y, int width, int height) {
		return (mouseX >= x && mouseX <= width) && (mouseY >= y && mouseY <= height);
	}
	
	@Override
	public void onGuiClosed() {
		mc.gameSettings.saveOptions();
	}
	
	public int startDragX0 = 0;
	public int startDragY0 = 0;
	public int endDragX0 = 0;
	public int endDragY0 = 0;
	public boolean debounce0 = false;
	public int startDragX1 = 0;
	public int startDragY1 = 0;
	public int endDragX1 = 0;
	public int endDragY1 = 0;
	public boolean debounce1 = false;
	Category moved = null;
	Category someoneElseDragging = null;
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button) {
		if(EaglerAdapter.mouseIsButtonDown(0) && debounce0 == false) {
			startDragX0 = mouseX;
			startDragY0 = mouseY;
			debounce0 = true;
		} else if(!EaglerAdapter.mouseIsButtonDown(0) && debounce0 == true) {
			debounce0 = false;
			endDragX0 = mouseX;
			endDragY0 = mouseY;
		} else if(debounce0 == true) {
			endDragX0 = mouseX;
			endDragY0 = mouseY;
		}
		if(EaglerAdapter.mouseIsButtonDown(1) && debounce1 == false) {
			startDragX1 = mouseX;
			startDragY1 = mouseY;
			debounce1 = true;
		} else if(!EaglerAdapter.mouseIsButtonDown(1) && debounce1 == true) {
			debounce1 = false;
			endDragX1 = mouseX;
			endDragY1 = mouseY;
		} else if(debounce1 == true) {
			endDragX1 = mouseX;
			endDragY1 = mouseY;
		}
		
		for(Category c : Category.values()) {
			if(c.name == "CategoryList") {
				int count = 1;
				for(Category cc : Category.values()) {
					if(cc.name != "CategoryList") {
						if(isMouseInside(mouseX, mouseY, c.x, c.y+20*count, c.x+c.maxWidth, c.y+20*count+20) && isMouseInside(startDragX0, startDragY0,c.x, c.y+20*count, c.x+c.maxWidth, c.y+20*count+20) && EaglerAdapter.mouseIsButtonDown(0) && c.hideDebounce == false) {
							c.hideDebounce = true;
						} else if(!EaglerAdapter.mouseIsButtonDown(0) && isMouseInside(endDragX0, endDragY0,c.x, c.y+20*count, c.x+c.maxWidth, c.y+20*count+20) && c.hideDebounce) {
							c.hideDebounce = false;
							cc.shown = !cc.shown;
						}
					}
					count++;
				}
			} else if(isMouseInside(mouseX, mouseY,c.x,c.y,c.x+c.maxWidth,c.y+20) && isMouseInside(startDragX1, startDragY1,c.x,c.y,c.x+c.maxWidth,c.y+20) && c.shown && EaglerAdapter.mouseIsButtonDown(1) && c.hideDebounce == false) {
				c.hideDebounce = true;
			} else if(c.shown && !EaglerAdapter.mouseIsButtonDown(1) && isMouseInside(endDragX1, endDragY1,c.x,c.y,c.x+c.maxWidth,c.y+20) && c.hideDebounce == true) {
				c.hideDebounce = false;
				c.collapsed = !c.collapsed;
			}
			if(c.shown) {
				
				boolean mouseOver = mouseX >= c.getX() && mouseY >= c.getY() && mouseX < c.getX() + c.maxWidth && mouseY < c.getY() + 20;
				boolean mouseOverX = (mouseX >= c.x && mouseX <= c.x+c.maxWidth); 
		        boolean mouseOverY = (mouseY >= c.y && mouseY <= c.y+20);
		        boolean drag = (mouseOverX && mouseOverY && EaglerAdapter.mouseIsButtonDown(0));
				if (c.dragging && (someoneElseDragging == null || someoneElseDragging == c)) {
					moved = c;
					if(!EaglerAdapter.mouseIsButtonDown(0)) { c.dragging = false; someoneElseDragging = null; }
		        }
				if(drag && (someoneElseDragging == c || someoneElseDragging == null)) {
					if (!c.dragging) {
		        		moved = c;
		        		someoneElseDragging = c;
		        	}
				}
			}
			if((c.name != "CategoryList")) {
				if(c.shown) {
					if(!c.collapsed) {
						int count = 1;
						for(Mod m : Client.INSTANCE.modManager.getModulesByCategory(c)) {
							if(c.moduleSettingsWatching == null) {
								if(isMouseInside(mouseX, mouseY, c.x, c.y+20*count, c.x + c.maxWidth, c.y+20*count+20) && isMouseInside(startDragX0, startDragY0, c.x, c.y+20*count, c.x + c.maxWidth, c.y+20*count+20) && isMouseInside(endDragX0, endDragY0, c.x, c.y+20*count, c.x + c.maxWidth, c.y+20*count+20) && EaglerAdapter.mouseIsButtonDown(0) && !m.debounceClickGUI) {
									m.debounceClickGUI = true;
								} else if(!EaglerAdapter.mouseIsButtonDown(0) && m.debounceClickGUI) {
									m.debounceClickGUI = false;
									m.toggle();
								}
								if(isMouseInside(mouseX, mouseY, c.x, c.y+20*count, c.x + c.maxWidth, c.y+20*count+20) && EaglerAdapter.mouseIsButtonDown(1) && !m.debounceClickGUI1) {
									m.debounceClickGUI1 = true;
								} else if(!EaglerAdapter.mouseIsButtonDown(1) && m.debounceClickGUI1) {
									m.debounceClickGUI1 = false;
									c.moduleSettingsWatching = m;
								}
							} else {
								int count1 = 1;
								boolean hasAlreadyOneDrag = false;
								for(Setting s : c.moduleSettingsWatching.settings) {
									if(s.shouldDraw()) {
										if(s instanceof NumberSetting) {
											NumberSetting ss = (NumberSetting) s;
											if((isMouseInside(mouseX, mouseY, c.x+4, c.y+2+count1*20+20-7, (c.x+4)+(c.maxWidth-6), (c.y+2+count1*20+20-7)+(2)) || ss.clickguiDebounce) && EaglerAdapter.mouseIsButtonDown(0) && !hasAlreadyOneDrag) {
												float v = ((float)((mouseX+2+2-c.x))/(float)(c.maxWidth));
												ss.setValue(v*((ss.getMax()-ss.getMin())+ss.getMin()));
												ss.clickguiDebounce = true;
												hasAlreadyOneDrag = true;
											} else {
												ss.clickguiDebounce = false;
												hasAlreadyOneDrag = false;
											}
										}
										if(s instanceof BooleanSetting) {
											if(isMouseInside(mouseX, mouseY, c.x, c.y+20*count1, c.x + c.maxWidth, c.y+20*count1+20) && isMouseInside(startDragX0, startDragY0, c.x, c.y+20*count1, c.x + c.maxWidth, c.y+20*count1+20) && EaglerAdapter.mouseIsButtonDown(0) && !s.clickguiDebounce) {
												((BooleanSetting) s).setValue(!((BooleanSetting)s).value);
												s.clickguiDebounce = true;
											} else if(!EaglerAdapter.mouseIsButtonDown(0) && s.clickguiDebounce) {
												s.clickguiDebounce = false;
											}
										}
										if(s instanceof ModeSetting) {
											if(isMouseInside(mouseX, mouseY, c.x, c.y+20*count1, c.x + c.maxWidth, c.y+20*count1+20) && isMouseInside(startDragX0, startDragY0, c.x, c.y+20*count1, c.x + c.maxWidth, c.y+20*count1+20) && EaglerAdapter.mouseIsButtonDown(0) && !s.clickguiDebounce) {
												((ModeSetting) s).next();
												s.clickguiDebounce = true;
											} else if(!EaglerAdapter.mouseIsButtonDown(0) && s.clickguiDebounce) {
												s.clickguiDebounce = false;
											}
										}
										count1++;
									}
								}
								boolean cancelFocus = false;
								if(c.moduleSettingsWatching.keyCode.getKeyCode() != 0) {
									if(isMouseInside(mouseX, mouseY, c.x+c.maxWidth - mc.fontRenderer.getStringWidth("X") - 5, c.y+20+(count*20-20/2-mc.fontRenderer.FONT_HEIGHT/2),c.x+c.maxWidth - 5,c.y+20+(count*20-20/2+mc.fontRenderer.FONT_HEIGHT/2)) && EaglerAdapter.mouseIsButtonDown(0)) {
										c.moduleSettingsWatching.keyCode.setKeyCode(0);
										cancelFocus = true;
									}
								}
								if(!cancelFocus && isMouseInside(mouseX, mouseY, c.x, c.y+20*count1, c.x + c.maxWidth, c.y+20*count1+20) && isMouseInside(startDragX0, startDragY0, c.x, c.y+20*count1, c.x + c.maxWidth, c.y+20*count1+20) && EaglerAdapter.mouseIsButtonDown(0) && !c.moduleSettingsWatching.keyCode.clickguiDebounce) {
									for(Category cc : Category.values()) {
										if(cc != c) {
											if(cc.moduleSettingsWatching != null) {
												cc.moduleSettingsWatching.keyCode.clickGuiFocused = false;
											}
										}
									}
									c.moduleSettingsWatching.keyCode.clickGuiFocused = !c.moduleSettingsWatching.keyCode.clickGuiFocused;
									c.moduleSettingsWatching.keyCode.clickguiDebounce = true;
								} else if(!EaglerAdapter.mouseIsButtonDown(0) && c.moduleSettingsWatching.keyCode.clickguiDebounce && !cancelFocus) {
									c.moduleSettingsWatching.keyCode.clickguiDebounce = false;
								}
							}
							if(isMouseInside(mouseX, mouseY, c.x+5, (c.y+20/2)-Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT/2,c.x+5+Minecraft.getMinecraft().fontRenderer.getStringWidth("<"), (c.y+20/2)+Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT/2) && EaglerAdapter.mouseIsButtonDown(0) && !c.debounceBackButton) {
								c.debounceBackButton = true;
							} else if(!EaglerAdapter.mouseIsButtonDown(0) && c.debounceBackButton && c.moduleSettingsWatching != null) {
								c.debounceBackButton = false;
								c.moduleSettingsWatching.keyCode.clickGuiFocused = false;
								c.moduleSettingsWatching = null;
							}
							
							count++;
						}
					}
				}
			}
			
		}
		
		
		if(moved != null) {
			Category c = moved;
			boolean mouseOver = mouseX >= c.getX() && mouseY >= c.getY() && mouseX < c.getX() + c.maxWidth && mouseY < c.getY() + 20;
			boolean mouseOverX = (mouseX >= c.x && mouseX <= c.x+c.maxWidth); 
	        boolean mouseOverY = (mouseY >= c.y && mouseY <= c.y+20);
	        boolean drag = (mouseOverX && mouseOverY && EaglerAdapter.mouseIsButtonDown(0));
			if (c.dragging && (someoneElseDragging == c || someoneElseDragging == null)) {
	            c.x = mouseX + c.lastX;
	            c.y = mouseY + c.lastY;
	            if(!EaglerAdapter.mouseIsButtonDown(0)) { c.dragging = false;someoneElseDragging = null; }
	        }
			if(drag && (someoneElseDragging == c || someoneElseDragging == null)) {
				if (!c.dragging) {
	        		c.lastX = c.getX() - mouseX;
	        		c.lastY = c.getY() - mouseY;
	        		c.dragging = true;
	        		someoneElseDragging = c;
	        	}
			}
		}
		super.mouseClicked(mouseX, mouseY, button);
	}
	
	public void mouseInput(int mouseX, int mouseY) {
		if(EaglerAdapter.mouseIsButtonDown(0) && debounce0 == false) {
			startDragX0 = mouseX;
			startDragY0 = mouseY;
			debounce0 = true;
		} else if(!EaglerAdapter.mouseIsButtonDown(0) && debounce0 == true) {
			debounce0 = false;
			endDragX0 = mouseX;
			endDragY0 = mouseY;
		} else if(debounce0 == true) {
			endDragX0 = mouseX;
			endDragY0 = mouseY;
		}
		if(EaglerAdapter.mouseIsButtonDown(1) && debounce1 == false) {
			startDragX1 = mouseX;
			startDragY1 = mouseY;
			debounce1 = true;
		} else if(!EaglerAdapter.mouseIsButtonDown(1) && debounce1 == true) {
			debounce1 = false;
			endDragX1 = mouseX;
			endDragY1 = mouseY;
		} else if(debounce1 == true) {
			endDragX1 = mouseX;
			endDragY1 = mouseY;
		}
		
		for(Category c : Category.values()) {
			if(c.name == "CategoryList") {
				int count = 1;
				for(Category cc : Category.values()) {
					if(cc.name != "CategoryList") {
						if(isMouseInside(mouseX, mouseY, c.x, c.y+20*count, c.x+c.maxWidth, c.y+20*count+20) && isMouseInside(startDragX0, startDragY0,c.x, c.y+20*count, c.x+c.maxWidth, c.y+20*count+20) && EaglerAdapter.mouseIsButtonDown(0) && c.hideDebounce == false) {
							c.hideDebounce = true;
						} else if(!EaglerAdapter.mouseIsButtonDown(0) && isMouseInside(endDragX0, endDragY0,c.x, c.y+20*count, c.x+c.maxWidth, c.y+20*count+20) && c.hideDebounce) {
							c.hideDebounce = false;
							cc.shown = !cc.shown;
						}
					}
					count++;
				}
			} else if(isMouseInside(mouseX, mouseY,c.x,c.y,c.x+c.maxWidth,c.y+20) && isMouseInside(startDragX1, startDragY1,c.x,c.y,c.x+c.maxWidth,c.y+20) && c.shown && EaglerAdapter.mouseIsButtonDown(1) && c.hideDebounce == false) {
				c.hideDebounce = true;
			} else if(c.shown && !EaglerAdapter.mouseIsButtonDown(1) && isMouseInside(endDragX1, endDragY1,c.x,c.y,c.x+c.maxWidth,c.y+20) && c.hideDebounce == true) {
				c.hideDebounce = false;
				c.collapsed = !c.collapsed;
			}
			if(c.shown) {
				
				boolean mouseOver = mouseX >= c.getX() && mouseY >= c.getY() && mouseX < c.getX() + c.maxWidth && mouseY < c.getY() + 20;
				boolean mouseOverX = (mouseX >= c.x && mouseX <= c.x+c.maxWidth); 
		        boolean mouseOverY = (mouseY >= c.y && mouseY <= c.y+20);
		        boolean drag = (mouseOverX && mouseOverY && EaglerAdapter.mouseIsButtonDown(0));
				if (c.dragging && (someoneElseDragging == null || someoneElseDragging == c)) {
					moved = c;
					if(!EaglerAdapter.mouseIsButtonDown(0)) { c.dragging = false; someoneElseDragging = null; }
		        }
				if(drag && (someoneElseDragging == c || someoneElseDragging == null)) {
					if (!c.dragging) {
		        		moved = c;
		        		someoneElseDragging = c;
		        	}
				}
			}
			if((c.name != "CategoryList")) {
				if(c.shown) {
					if(!c.collapsed) {
						int count = 1;
						for(Mod m : Client.INSTANCE.modManager.getModulesByCategory(c)) {
							if(c.moduleSettingsWatching == null) {
								if(isMouseInside(mouseX, mouseY, c.x, c.y+20*count, c.x + c.maxWidth, c.y+20*count+20) && isMouseInside(startDragX0, startDragY0, c.x, c.y+20*count, c.x + c.maxWidth, c.y+20*count+20) && isMouseInside(endDragX0, endDragY0, c.x, c.y+20*count, c.x + c.maxWidth, c.y+20*count+20) && EaglerAdapter.mouseIsButtonDown(0) && !m.debounceClickGUI) {
									m.debounceClickGUI = true;
								} else if(!EaglerAdapter.mouseIsButtonDown(0) && m.debounceClickGUI) {
									m.debounceClickGUI = false;
									m.toggle();
								}
								if(isMouseInside(mouseX, mouseY, c.x, c.y+20*count, c.x + c.maxWidth, c.y+20*count+20) && EaglerAdapter.mouseIsButtonDown(1) && !m.debounceClickGUI1) {
									m.debounceClickGUI1 = true;
								} else if(!EaglerAdapter.mouseIsButtonDown(1) && m.debounceClickGUI1) {
									m.debounceClickGUI1 = false;
									c.moduleSettingsWatching = m;
								}
							} else {
								int count1 = 1;
								boolean hasAlreadyOneDrag = false;
								for(Setting s : c.moduleSettingsWatching.settings) {
									if(s.shouldDraw()) {
										if(s instanceof NumberSetting) {
											NumberSetting ss = (NumberSetting) s;
											if((isMouseInside(mouseX, mouseY, c.x+4, c.y+2+count1*20+20-7, (c.x+4)+(c.maxWidth-6), (c.y+2+count1*20+20-7)+(2)) || ss.clickguiDebounce) && EaglerAdapter.mouseIsButtonDown(0) && !hasAlreadyOneDrag) {
												float v = ((float)((mouseX+2+2-c.x))/(float)(c.maxWidth));
												ss.setValue(v*((ss.getMax()-ss.getMin())+ss.getMin()));
												ss.clickguiDebounce = true;
												hasAlreadyOneDrag = true;
											} else {
												ss.clickguiDebounce = false;
												hasAlreadyOneDrag = false;
											}
										}
										if(s instanceof BooleanSetting) {
											if(isMouseInside(mouseX, mouseY, c.x, c.y+20*count1, c.x + c.maxWidth, c.y+20*count1+20) && isMouseInside(startDragX0, startDragY0, c.x, c.y+20*count1, c.x + c.maxWidth, c.y+20*count1+20) && EaglerAdapter.mouseIsButtonDown(0) && !s.clickguiDebounce) {
												((BooleanSetting) s).setValue(!((BooleanSetting)s).value);
												s.clickguiDebounce = true;
											} else if(!EaglerAdapter.mouseIsButtonDown(0) && s.clickguiDebounce) {
												s.clickguiDebounce = false;
											}
										}
										if(s instanceof ModeSetting) {
											if(isMouseInside(mouseX, mouseY, c.x, c.y+20*count1, c.x + c.maxWidth, c.y+20*count1+20) && isMouseInside(startDragX0, startDragY0, c.x, c.y+20*count1, c.x + c.maxWidth, c.y+20*count1+20) && EaglerAdapter.mouseIsButtonDown(0) && !s.clickguiDebounce) {
												((ModeSetting) s).next();
												s.clickguiDebounce = true;
											} else if(!EaglerAdapter.mouseIsButtonDown(0) && s.clickguiDebounce) {
												s.clickguiDebounce = false;
											}
										}
										count1++;
									}
								}
								boolean cancelFocus = false;
								if(c.moduleSettingsWatching.keyCode.getKeyCode() != 0) {
									if(isMouseInside(mouseX, mouseY, c.x+c.maxWidth - mc.fontRenderer.getStringWidth("X") - 5, c.y+20+(count*20-20/2-mc.fontRenderer.FONT_HEIGHT/2),c.x+c.maxWidth - 5,c.y+20+(count*20-20/2+mc.fontRenderer.FONT_HEIGHT/2)) && EaglerAdapter.mouseIsButtonDown(0)) {
										c.moduleSettingsWatching.keyCode.setKeyCode(0);
										cancelFocus = true;
									}
								}
								if(!cancelFocus && isMouseInside(mouseX, mouseY, c.x, c.y+20*count1, c.x + c.maxWidth, c.y+20*count1+20) && isMouseInside(startDragX0, startDragY0, c.x, c.y+20*count1, c.x + c.maxWidth, c.y+20*count1+20) && EaglerAdapter.mouseIsButtonDown(0) && !c.moduleSettingsWatching.keyCode.clickguiDebounce) {
									for(Category cc : Category.values()) {
										if(cc != c) {
											if(cc.moduleSettingsWatching != null) {
												cc.moduleSettingsWatching.keyCode.clickGuiFocused = false;
											}
										}
									}
									c.moduleSettingsWatching.keyCode.clickGuiFocused = !c.moduleSettingsWatching.keyCode.clickGuiFocused;
									c.moduleSettingsWatching.keyCode.clickguiDebounce = true;
								} else if(!EaglerAdapter.mouseIsButtonDown(0) && c.moduleSettingsWatching.keyCode.clickguiDebounce && !cancelFocus) {
									c.moduleSettingsWatching.keyCode.clickguiDebounce = false;
								}
							}
							if(isMouseInside(mouseX, mouseY, c.x+5, (c.y+20/2)-Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT/2,c.x+5+Minecraft.getMinecraft().fontRenderer.getStringWidth("<"), (c.y+20/2)+Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT/2) && EaglerAdapter.mouseIsButtonDown(0) && !c.debounceBackButton) {
								c.debounceBackButton = true;
							} else if(!EaglerAdapter.mouseIsButtonDown(0) && c.debounceBackButton && c.moduleSettingsWatching != null) {
								c.debounceBackButton = false;
								c.moduleSettingsWatching.keyCode.clickGuiFocused = false;
								c.moduleSettingsWatching = null;
							}
							
							count++;
						}
					}
				}
			}
			
		}
		
		
		if(moved != null) {
			Category c = moved;
			boolean mouseOver = mouseX >= c.getX() && mouseY >= c.getY() && mouseX < c.getX() + c.maxWidth && mouseY < c.getY() + 20;
			boolean mouseOverX = (mouseX >= c.x && mouseX <= c.x+c.maxWidth); 
	        boolean mouseOverY = (mouseY >= c.y && mouseY <= c.y+20);
	        boolean drag = (mouseOverX && mouseOverY && EaglerAdapter.mouseIsButtonDown(0));
			if (c.dragging && (someoneElseDragging == c || someoneElseDragging == null)) {
	            c.x = mouseX + c.lastX;
	            c.y = mouseY + c.lastY;
	            if(!EaglerAdapter.mouseIsButtonDown(0)) { c.dragging = false;someoneElseDragging = null; }
	        }
			if(drag && (someoneElseDragging == c || someoneElseDragging == null)) {
				if (!c.dragging) {
	        		c.lastX = c.getX() - mouseX;
	        		c.lastY = c.getY() - mouseY;
	        		c.dragging = true;
	        		someoneElseDragging = c;
	        	}
			}
		}
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
		mouseInput(par1, par2);
		Client.INSTANCE.primaryColor = 0xFFA91B60;
		Client.INSTANCE.secondaryColor = 0xFFFF0080;
		for(int i = Category.values().length-1; i >= 0; i--) {
			Category c = Category.values()[i];
			if(c.name == "CategoryList") {
				RenderUtils.drawRoundedRect(c.x, c.y, c.maxWidth, 20+10, 2, 0xFF151213);
				RenderUtils.drawRoundedRect(c.x, c.y+20, c.maxWidth, Category.values().length*20-20+5, 1, 0xFF1A1A1A);
				Gui.drawRect(c.x, c.y+19, c.x+c.maxWidth, c.y+20+10, 0xFF1A1A1A);
				fr.drawString("Fuchsia ", c.x+5, (c.y+20/2)-fr.FONT_HEIGHT/2, -1);
				fr.drawString("X", c.x+5+fr.getStringWidth("Fuchsia "), (c.y+20/2)-fr.FONT_HEIGHT/2, 0xFFA91B60);
				int count = 1;
				for(Category cc : Category.values()) {
					if(cc.name != "CategoryList") {
						if(cc.shown) {
							Gui.drawRect(c.x, c.y+20*count, c.x + c.maxWidth, c.y+20*count+20, 0xFF1E1E1E);
							fr.drawString(cc.name, c.x+5, (c.y + (20*count)+20/2-fr.FONT_HEIGHT/2), Client.INSTANCE.primaryColor);
						} else {
							fr.drawString(cc.name, c.x+5, (c.y + (20*count)+20/2-fr.FONT_HEIGHT/2), -1);
						}
					}
					count++;
				}
			} else {
				if(c.shown) {
					if(c.moduleSettingsWatching != null) {
						if(c.collapsed) {
							RenderUtils.drawRoundedRect(c.x, c.y, c.maxWidth, 20, 1, 0xFF151213);
							fr.drawString(c.name, c.x+5, (c.y+20/2)-fr.FONT_HEIGHT/2, -1);
							fr.drawString(">", c.x+c.maxWidth-5-fr.getStringWidth(">"), (c.y+20/2)-fr.FONT_HEIGHT/2, -1);
						} else {
							RenderUtils.drawRoundedRect(c.x, c.y, c.maxWidth, 20+10, 1, 0xFF151213);
							int settings = 0;
							for(Setting s : c.moduleSettingsWatching.settings) {
								if(s.shouldDraw()) {
									++settings;
								}
							}
							RenderUtils.drawRoundedRect(c.x, c.y+20, c.maxWidth, settings*20+20+5, 1, 0xFF1A1A1A);
							Gui.drawRect(c.x, c.y+19, c.x+c.maxWidth, c.y+20+10, 0xFF1A1A1A);
							fr.drawString("< "+c.name, c.x+5, (c.y+20/2)-fr.FONT_HEIGHT/2, -1);
							fr.drawString("<", c.x+c.maxWidth-5-fr.getStringWidth("<"), (c.y+20/2)-fr.FONT_HEIGHT/2, -1);
							int count = 1;
							for(Setting s : c.moduleSettingsWatching.settings) {
								if(s.shouldDraw()) {
									if(s instanceof NumberSetting) {
										NumberSetting ss = (NumberSetting) s;
										fr.drawString(s.name+": ", c.x+4, c.y+2+count*20, -1);
										fr.drawString(String.valueOf(((NumberSetting) s).getValue()), c.x+c.maxWidth - fr.getStringWidth(String.valueOf(((NumberSetting) s).getValue()))-4, c.y+2+count*20, -1);
										RenderUtils.drawRoundedRect(c.x+4, c.y+2+count*20+20-7, c.maxWidth-2-2-2, 2, 1, Client.INSTANCE.secondaryColor);
										RenderUtils.drawRoundedRect(c.x+(int)(((ss.getValue() - ss.getMin()) / (ss.getMax() - ss.getMin()))*(c.maxWidth-6)), c.y+2+count*20+20-9, 6, 5, 1, Client.INSTANCE.primaryColor);
									}
									if(s instanceof BooleanSetting) {
										fr.drawString(s.name + ": ", c.x+5, (c.y + (20*count)+20/2-fr.FONT_HEIGHT/2), -1);
										RenderUtils.drawRoundedRect(c.x+c.maxWidth - 30, (c.y + (20*count)+20/2-fr.FONT_HEIGHT/2), 20, 7, 2, Client.INSTANCE.secondaryColor);
										if(((BooleanSetting) s).getValue()) {
											RenderUtils.drawRoundedRect(c.x+c.maxWidth - 30 + 12, (c.y + (20*count)+20/2-fr.FONT_HEIGHT/2)+1, 6, 5, 1, Client.INSTANCE.primaryColor);
										} else
											RenderUtils.drawRoundedRect(c.x+c.maxWidth - 30 + 2, (c.y + (20*count)+20/2-fr.FONT_HEIGHT/2)+1, 6, 5, 1, Client.INSTANCE.primaryColor);
										
									}
									if(s instanceof ModeSetting) {
										ModeSetting ss = (ModeSetting) s;
										//Gui.drawRect(c.x, c.y+20*count, c.x + c.maxWidth, c.y+20*count+20, Client.INSTANCE.primaryColor);
										fr.drawString(ss.name + ": " + ss.current, c.x+5, (c.y + (20*count)+20/2-fr.FONT_HEIGHT/2), -1);
									}
									count++;
								}
							}
							if(c.moduleSettingsWatching.keyCode.clickGuiFocused) {
								Gui.drawRect(c.x, c.y+20*count, c.x + c.maxWidth, c.y+20*count+20, Client.INSTANCE.primaryColor);
							}
							fr.drawString(c.moduleSettingsWatching.keyCode.name + ": " + mc.gameSettings.getKeyDisplayString(c.moduleSettingsWatching.keyCode.getKeyCode()), c.x+5, (c.y + (20*count)+20/2-fr.FONT_HEIGHT/2), -1);
							if(c.moduleSettingsWatching.keyCode.getKeyCode() != 0) {
								fr.drawString("X", c.x+c.maxWidth - fr.getStringWidth("X") - 5, (c.y + (20*count)+20/2-fr.FONT_HEIGHT/2), -1);
							}
						}
					} else {
						if(c.collapsed) {
							RenderUtils.drawRoundedRect(c.x, c.y, c.maxWidth, 20, 1, 0xFF151213);
							fr.drawString(c.name, c.x+5, (c.y+20/2)-fr.FONT_HEIGHT/2, -1);
							fr.drawString(">", c.x+c.maxWidth-5-fr.getStringWidth(">"), (c.y+20/2)-fr.FONT_HEIGHT/2, -1);
						} else {
							RenderUtils.drawRoundedRect(c.x, c.y, c.maxWidth, 20+10, 1, 0xFF151213);
							RenderUtils.drawRoundedRect(c.x, c.y+20, c.maxWidth, Client.INSTANCE.modManager.getModulesByCategory(c).size()*20+5, 1, 0xFF1A1A1A);
							Gui.drawRect(c.x, c.y+19, c.x+c.maxWidth, c.y+20+10, 0xFF1A1A1A);
							fr.drawString(c.name, c.x+5, (c.y+20/2)-fr.FONT_HEIGHT/2, -1);
							fr.drawString("<", c.x+c.maxWidth-5-fr.getStringWidth("<"), (c.y+20/2)-fr.FONT_HEIGHT/2, -1);
							int count = 1;
							for(Mod m : Client.INSTANCE.modManager.getModulesByCategory(c)) {
								if(m.isEnabled()) {
									//Gui.drawRect(c.x, c.y+20*count, c.x + c.maxWidth, c.y+20*count+20, Client.INSTANCE.primaryColor);
									RenderUtils.drawChromaRectangle(c.x, c.y+20*count, c.x + c.maxWidth, c.y+20*count+20);
									fr.drawString(m.name, c.x+5, (c.y + (20*count)+20/2-fr.FONT_HEIGHT/2), -1);
								} else {
									fr.drawString(m.name, c.x+5, (c.y + (20*count)+20/2-fr.FONT_HEIGHT/2), -1);
								}
								count++;
							}
							count = 1;
							for(Mod m : Client.INSTANCE.modManager.getModulesByCategory(c)) {
								if(m.description != "" && isMouseInside(par1, par2, c.x, c.y+20*count, c.x + c.maxWidth, c.y+20*count+20) && c.moduleSettingsWatching == null) {
									Gui.drawRect(par1-1+7,par2-1, par1+fr.getStringWidth(m.description)+1+7, par2+fr.FONT_HEIGHT+1, 0xFF1A1A1A);
									fr.drawString(m.description, par1+7, par2, -1);
								}
								count++;
							}
						}
					}
				}
			}
		}
		for(Category c : Category.values()) {
			if(c.name != "CategoryList") {
				if(!c.collapsed) {
					int  count = 1;
					for(Mod m : Client.INSTANCE.modManager.getModulesByCategory(c)) {
						if(m.description != "" && isMouseInside(par1, par2, c.x, c.y+20*count, c.x + c.maxWidth, c.y+20*count+20)) {
							Gui.drawRect(par1-1+7,par2-1, par1+fr.getStringWidth(m.description)+1+7, par2+fr.FONT_HEIGHT+1, 0xFF1A1A1A);
							fr.drawString(m.description, par1+7, par2, -1);
						}
						count++;
					}
				}
			}
		}
		super.drawScreen(par1, par2, par3);
	}
	
	@Override
	protected void keyTyped(char par1, int par2) {
		if(par2 == 0x15 || par2 == KeyboardUtils.KEY_ESCAPE) {
			mc.displayGuiScreen(null);
		}
		for(Category c : Category.values()) {
			if(c.moduleSettingsWatching != null && c.moduleSettingsWatching.keyCode.clickGuiFocused) {
				c.moduleSettingsWatching.keyCode.clickGuiFocused = false;
				c.moduleSettingsWatching.keyCode.setKeyCode(par2);
			}
		}
		super.keyTyped(par1, par2);
	}
	
	
	@Override
	public void initGui() {
		/*int count = 2;
		for(Mod m : Client.INSTANCE.modManager.modules) {
			this.buttonList.add(new GuiButton(count, 0, count*20, m.name));
			++count;
		}*/
		this.buttonList.add(new GuiButton(2, this.width-100, this.height-20, 100,20,"Modify HUD"));
		super.initGui();
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		/*int count = 2;
		for(Mod m : Client.INSTANCE.modManager.modules) {
			if(par1GuiButton.id == count) {
				m.toggle();
			}
			++count;
		}*/
		if(par1GuiButton.id == 2) {
			mc.displayGuiScreen(new HUDConfigScreen());
		}
		super.actionPerformed(par1GuiButton);
	}

}
