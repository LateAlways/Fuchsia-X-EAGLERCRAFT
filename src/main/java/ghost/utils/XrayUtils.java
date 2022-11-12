package ghost.utils;

import java.util.ArrayList;

import ghost.mods.impl.render.Xray;
import net.minecraft.src.Block;

public class XrayUtils {
	
	public static ArrayList<Block> xrayBlocks = new ArrayList<Block>();
	
	public static void initXRayBlocks() {
		xrayBlocks.add(Block.oreCoal);
		xrayBlocks.add(Block.oreIron);
		xrayBlocks.add(Block.oreGold);
		xrayBlocks.add(Block.oreRedstone);
		xrayBlocks.add(Block.oreLapis);
		xrayBlocks.add(Block.oreDiamond);
		xrayBlocks.add(Block.oreEmerald);
		xrayBlocks.add(Block.oreNetherQuartz);
		xrayBlocks.add(Block.blockClay);
		xrayBlocks.add(Block.glowStone);
		xrayBlocks.add(Block.workbench);
		xrayBlocks.add(Block.torchWood);
		xrayBlocks.add(Block.ladder);
		xrayBlocks.add(Block.tnt);
		xrayBlocks.add(Block.blockIron);
		xrayBlocks.add(Block.blockGold);
		xrayBlocks.add(Block.blockDiamond);
		xrayBlocks.add(Block.blockEmerald);
		xrayBlocks.add(Block.blockRedstone);
		xrayBlocks.add(Block.blockLapis);
		xrayBlocks.add(Block.fire);
		xrayBlocks.add(Block.cobblestoneMossy);
		xrayBlocks.add(Block.mobSpawner);
		xrayBlocks.add(Block.endPortalFrame);
		xrayBlocks.add(Block.enchantmentTable);
		xrayBlocks.add(Block.bookShelf);
		xrayBlocks.add(Block.commandBlock);

		xrayBlocks.add(Block.getBlockById(74));
		xrayBlocks.add(Block.getBlockById(8));
		xrayBlocks.add(Block.getBlockById(9));
		xrayBlocks.add(Block.getBlockById(10));
		xrayBlocks.add(Block.getBlockById(11));
		xrayBlocks.add(Block.getBlockById(61));
		xrayBlocks.add(Block.getBlockById(62));
	}
	
	public static boolean isXrayBlock(Block b) {
		if(xrayBlocks.contains(b)) {
			return true;
		}
		return false;
	}

}
