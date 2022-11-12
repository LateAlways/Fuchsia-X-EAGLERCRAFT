package ghost.mods.impl.player;

import ghost.events.Event;
import ghost.events.EventType;
import ghost.events.impl.EventMotion;
import ghost.events.impl.EventUpdate;
import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;
import ghost.mods.essential.settings.NumberSetting;
import ghost.utils.Timer;
import net.minecraft.src.Enchantment;
import net.minecraft.src.EnchantmentHelper;
import net.minecraft.src.GuiInventory;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemArmor;
import net.minecraft.src.ItemEgg;
import net.minecraft.src.ItemGlassBottle;
import net.minecraft.src.ItemPotion;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ItemSword;
import net.minecraft.src.Packet100OpenWindow;
import net.minecraft.src.Packet101CloseWindow;
import net.minecraft.src.Potion;
import net.minecraft.src.PotionEffect;

public class InventoryCleaner extends Mod {

	public NumberSetting delay = new NumberSetting("Delay").withValue(180f).withMin(0).withIncrement(1).withMax(500);
	
	public boolean cleaning, equipping;
	
	private Timer timer = new Timer();
	
	public InventoryCleaner() {
		super("InventoryCleaner", "Removes unnecessary items from your inventory.", Category.MISC, true);
	}
	
	public void drop(int i) {
		mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, i, 0, 0, mc.thePlayer);
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, -999, 0, 0, mc.thePlayer);
	}
	
	@Override
	public void onEvent(Event e) {
		if(e instanceof EventUpdate && e.getType() == EventType.PRE) {
			
	        if ((mc.currentScreen == null || mc.currentScreen instanceof GuiInventory)) {
	            for (int i = 0; i < 45; ++i) {
	                if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
	                    ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
	                    if(is.getItem() instanceof ItemArmor) {
	                    	
	                    }
	                }

	                if (i == 44 && cleaning) {
	                    mc.thePlayer.sendQueue.addToSendQueue(new Packet101CloseWindow(0));
	                    cleaning = false;
	                }
	            }
	        }
		}
		super.onEvent(e);
	}
	
}
