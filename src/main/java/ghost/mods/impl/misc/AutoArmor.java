package ghost.mods.impl.misc;

import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;
import net.minecraft.src.Enchantment;
import net.minecraft.src.EnchantmentHelper;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.GuiInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemArmor;
import net.minecraft.src.ItemStack;

public class AutoArmor extends Mod {

	public AutoArmor() {
		super("AutoArmor","Equips armor automatically.", Category.MISC, true);
	}
	
	private int[] bestArmor;
	int delay;
	private int[] boots;
	private int[] chestplate;
	private int[] helmet;
	private int[] leggings;
	
	private int getItem(final int id) {
		for (int index = 9; index < 45; ++index) {
			final ItemStack item = this.mc.thePlayer.inventoryContainer.getSlot(index).getStack();
			if (item != null && item.getItem().itemID == id) {
				return index;
			}
		}
		return -1;
	}

	private int getProt(ItemStack stack) {
		if ((stack != null) && ((stack.getItem() instanceof ItemArmor))) {
			int normalValue = ((ItemArmor) stack.getItem()).damageReduceAmount;
			int enchantmentValue = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack);
			return normalValue + enchantmentValue;
		}
		return -1;
	}

	
	@Override
	public void onUpdate() {
		this.boots = new int[] { Item.bootsDiamond.itemID, Item.bootsGold.itemID, Item.bootsChain.itemID, Item.bootsIron.itemID, Item.bootsLeather.itemID };
		this.chestplate = new int[] { Item.plateDiamond.itemID, Item.plateGold.itemID, Item.plateChain.itemID, Item.plateIron.itemID, Item.plateLeather.itemID };
		this.helmet = new int[] { Item.helmetDiamond.itemID, Item.helmetGold.itemID, Item.helmetChain.itemID, Item.helmetIron.itemID, Item.helmetLeather.itemID };
		this.leggings = new int[] { Item.legsDiamond.itemID, Item.legsGold.itemID, Item.legsChain.itemID, Item.legsIron.itemID, Item.legsLeather.itemID };

		this.delay = 0;

		if (!(mc.currentScreen instanceof GuiInventory)) {
			return;
		}
		if (this.delay <= 0) {
			for (int i = 5; i < 9; i++) {
				ItemStack stack = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
				int value = getProt(stack);
				int type = i - 5;
				int bestSlot = -1;
				int highestValue = 0;
				for (int inv = 0; inv < 45; inv++) {
					ItemStack invStx = this.mc.thePlayer.inventoryContainer.getSlot(inv).getStack();
					if ((invStx != null) && ((invStx.getItem() instanceof ItemArmor))) {
						ItemArmor armour = (ItemArmor) invStx.getItem();
						int armourProtection = armour.damageReduceAmount
								+ EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, invStx);
						if ((armour.armorType == type) && (armourProtection > value)
								&& (armourProtection > highestValue)) {
							highestValue = armourProtection;
							bestSlot = inv;
						}
					}
				}
				if (bestSlot != -1) {
					if (stack == null) {
						this.mc.playerController.windowClick(0, bestSlot, 0, 1, this.mc.thePlayer);
					} else {
						this.mc.playerController.windowClick(0, i, 1, 4, this.mc.thePlayer);
						this.mc.playerController.windowClick(0, bestSlot, 0, 1, this.mc.thePlayer);
					}
					return;
				} else {
					for (int inv = 0; inv < 45; inv++) {
						ItemStack invStx = this.mc.thePlayer.inventoryContainer.getSlot(inv).getStack();
						if ((invStx != null) && ((invStx.getItem() instanceof ItemArmor))) {
							ItemArmor armour = (ItemArmor) invStx.getItem();
							int armourProtection = armour.damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, invStx);
							if(armourProtection < highestValue) {
								mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, inv, 0, 0, mc.thePlayer);
						        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, -999, 0, 0, mc.thePlayer);
							}
						}
					}
				}
				this.delay = 12;
			}
		} else {
			--this.delay;
		}
	}

}
