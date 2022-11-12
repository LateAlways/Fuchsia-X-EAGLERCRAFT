package ghost.utils;

import java.awt.Color;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.adapter.Tessellator;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiScreen;

public class RenderUtils {
	
	public static void drawRoundedRect(int x, int y, int width, int height, int round, int color) {
		int yy = 0;
		for(int xx = round; xx >= 1; --xx) {
			Gui.drawRect(x+xx, y+yy-1, x+(width-xx), y+yy-1+1, color);
			++y;
		}
		Gui.drawRect(x, y+yy-1, x+width, y+yy+(height-(round))-1, color);
		yy=0;
		for(int xx = 1; xx <= round; xx++) {
			Gui.drawRect(x+xx, y+yy+(height-(round))-1, x+(width-xx), y+yy+(height-(round))-1+1, color);
			++yy;
		}
	}

	public static void glVertex2d(double par1, double par2) {
		Tessellator instance = Tessellator.instance;

		instance.addVertex(par1, par2, 0);
	}
	
	public static int astolfoColorsDraw(int yOffset, int yTotal) {
        return astolfoColorsDraw(yOffset, yTotal, 50000f);
     }
	
	public static int HSBtoRGB(float hue, float saturation, float brightness) {
        int r = 0, g = 0, b = 0;
        if (saturation == 0) {
            r = g = b = (int) (brightness * 255.0f + 0.5f);
        } else {
            float h = (hue - (float)Math.floor(hue)) * 6.0f;
            float f = h - (float)java.lang.Math.floor(h);
            float p = brightness * (1.0f - saturation);
            float q = brightness * (1.0f - saturation * f);
            float t = brightness * (1.0f - (saturation * (1.0f - f)));
            switch ((int) h) {
            case 0:
                r = (int) (brightness * 255.0f + 0.5f);
                g = (int) (t * 255.0f + 0.5f);
                b = (int) (p * 255.0f + 0.5f);
                break;
            case 1:
                r = (int) (q * 255.0f + 0.5f);
                g = (int) (brightness * 255.0f + 0.5f);
                b = (int) (p * 255.0f + 0.5f);
                break;
            case 2:
                r = (int) (p * 255.0f + 0.5f);
                g = (int) (brightness * 255.0f + 0.5f);
                b = (int) (t * 255.0f + 0.5f);
                break;
            case 3:
                r = (int) (p * 255.0f + 0.5f);
                g = (int) (q * 255.0f + 0.5f);
                b = (int) (brightness * 255.0f + 0.5f);
                break;
            case 4:
                r = (int) (t * 255.0f + 0.5f);
                g = (int) (p * 255.0f + 0.5f);
                b = (int) (brightness * 255.0f + 0.5f);
                break;
            case 5:
                r = (int) (brightness * 255.0f + 0.5f);
                g = (int) (p * 255.0f + 0.5f);
                b = (int) (q * 255.0f + 0.5f);
                break;
            }
        }
        return 0xff000000 | (r << 16) | (g << 8) | (b << 0);
    }
	
	 public static int astolfoColorsDraw(int yOffset, int yTotal, float speed) {
         float hue = (float) (System.currentTimeMillis() % (int)speed) + ((yTotal - yOffset) * 9);
         while (hue > speed) {
            hue -= speed;
         }
         hue /= speed;
         if (hue > 0.5) {
            hue = 0.5F - (hue - 0.5f);
         }
         hue += 0.5F;
         return HSBtoRGB(hue, 0.5f, 1F);
      }
	
	public static void drawRectOutline(int x, int y, int width, int height, int color) {
		Gui.drawRect(x, y, width, y+1, color);
		Gui.drawRect(x, y, x+1, height, color);
		Gui.drawRect(width-1, y, width, height, color);
		Gui.drawRect(x, height-1, width, height, color);
	}
	
	public static void drawChromaRectangle(int x, int y, int width, int height) {
		int i = x;
		while(true) {
			if(i+10 <= width) {
				Gui.drawRect(i, y, i+10, height,RenderUtils.astolfoColorsDraw(i, GuiScreen.width,10000f));
			} else {
				break;
			}
			i+=10;
		}
		if(width-i != 0) {
			for(int h = i; h < width; h++) {
				Gui.drawRect(h, y, h+1, height,RenderUtils.astolfoColorsDraw(h, GuiScreen.width,10000f));
			}
		}
	}
	
	public static void renderChromaString(String text, int x, int y) {
		int xPos = x;
		String[] r = text.split("");
		for(String s : r) {
			Minecraft.getMinecraft().fontRenderer.drawString(s, xPos, y, RenderUtils.astolfoColorsDraw(xPos, GuiScreen.width, 10000f));
			xPos += Minecraft.getMinecraft().fontRenderer.getStringWidth(s);
		}
	}
	
	public static void drawRoundedRect(int x, int y, int width, int height, int round) {
		int yy = 0;
		for(int xx = round; xx >= 1; --xx) {
			Gui.drawRect(x+xx, y+yy-1, x+(width-xx), y+yy-1+1);
			++y;
		}
		Gui.drawRect(x, y+yy-1, x+width, y+yy+(height-(round))-1);
		yy=0;
		for(int xx = 1; xx <= round; xx++) {
			Gui.drawRect(x+xx, y+yy+(height-(round))-1, x+(width-xx), y+yy+(height-(round))-1+1);
			++yy;
		}
	}
}
