package ghost.mods.essential;

import ghost.Client;
import ghost.gui.ClickGUI;
import net.lax1dude.eaglercraft.EaglerAdapter;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiScreen;

public class RenderModule extends Mod {
	
	public boolean dragging = false;
	
	public RenderModule(String name, String description, Category cat, int x, int y, int width, int height) {
		super(name, description, cat);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public RenderModule(String name, String description, Category cat, int x, int y, int width, int height, boolean blatant) {
		super(name, description, cat, blatant);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.blatant = blatant;
	}
	
	public int x,y,width,height;
	private int lastX;
	private int lastY;

	public void draw() {
		
	}
	
	public void Resize() {
		if((getX() + getWidth()) > GuiScreen.width) {
			this.x = GuiScreen.width - getWidth();
			dragging = false;
		} else if ((getY() + getHeight()) > GuiScreen.height) {
			this.y = GuiScreen.height - getHeight();
			dragging = false;
		} else if ((getX()) < 0) {
			this.x = 0;
			dragging = false;
		} else if ((getY()) < 0) {
			this.y = 0;
			dragging = false;
		} else if (getX() <= 0 && getY() < 0) {
			this.y = 0;
			this.x = 0;
			dragging = false;
		} else if (getX() + getWidth() > GuiScreen.width && getY() < 0) {
			this.x = GuiScreen.width - getWidth();
			this.y = 0;
			dragging = false;
		} else if (getX() + getWidth() > GuiScreen.width && getY() + getHeight() > GuiScreen.height) {
			this.x = GuiScreen.width - getWidth();
			this.y = GuiScreen.height - getHeight();
			dragging = false;
		} else if (getY() + getHeight() > GuiScreen.height && getX() < 0) {
			this.x = GuiScreen.width - getWidth();
			this.y = GuiScreen.height - getHeight();
			dragging = false;
		}
	}
	
	private void draggingFix(int mouseX, int mouseY) {
        if (this.dragging) {
            this.x = mouseX + this.lastX;
            this.y = mouseY + this.lastY;
            if(!EaglerAdapter.mouseIsButtonDown(0)) this.dragging = false;
            
            if(this.x >= GuiScreen.width - getWidth()) {
            	this.dragging = false;
            }
            if(this.y >= GuiScreen.height - getHeight()) {
            	this.dragging = false;
            }
        }
    }
	
	public void renderLayout(int mouseX, int mouseY) {
		Resize();
		boolean mouseOver = mouseX >= getX() && mouseY >= getY() && mouseX < getX() + getWidth() && mouseY < getY() + this.getHeight();
		boolean mouseOverX = (mouseX >= this.x && mouseX <= this.x+this.getWidth()); 
        boolean mouseOverY = (mouseY >= this.y && mouseY <= this.y+this.getHeight());
        boolean drag = (mouseOverX && mouseOverY && EaglerAdapter.mouseIsButtonDown(0));
        draggingFix(mouseX, mouseY);
		if(drag) {
        	if (!this.dragging) {
        		this.lastX = x - mouseX;
        		this.lastY = y - mouseY;
        		this.dragging = true;
        	}
        }
		draw();
		Gui.drawRect(this.x, this.y, this.x + this.getWidth(), this.y+this.getHeight(), 0x50FFFFFF);
		Gui.drawRect(this.x, this.y, this.x+this.getWidth(), this.y+1, 0xFFFFFFFF);
		Gui.drawRect(this.x, this.y, this.x+1, this.y+getHeight(), 0xFFFFFFFF);
		Gui.drawRect(this.x+this.getWidth()-1, this.y, this.x+getWidth(), this.y+this.getHeight(), 0xFFFFFFFF);
		Gui.drawRect(this.x, this.y+this.getHeight()-1, this.x+getWidth(), this.y+this.getHeight(), 0xFFFFFFFF);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
}
