package ghost.mods.impl.misc;

import java.util.ArrayList;

import ghost.events.Event;
import ghost.events.EventType;
import ghost.events.impl.EventMotion;
import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;
import ghost.mods.essential.settings.BooleanSetting;
import ghost.mods.essential.settings.NumberSetting;
import ghost.utils.Timer;
import net.lax1dude.eaglercraft.EaglercraftRandom;
import net.minecraft.src.ContainerChest;
import net.minecraft.src.Entity;
import net.minecraft.src.EnumFacing;
import net.minecraft.src.GuiChest;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.Vec3;

public class ChestStealer extends Mod {

	public ChestStealer() {
		super("ChestStealer", "Automatically steals items from chests, really useful in survival games.", Category.MISC, true);
		this.addSetting(aura,chestStealDelay);
	}
	
	public NumberSetting chestStealDelay = new NumberSetting("Delay", "", 150,0,750,1,1);
	public BooleanSetting aura = new BooleanSetting("Aura", "", false);
	
	ArrayList<TileEntityChest> openedChests = new ArrayList<TileEntityChest>();
	private boolean stole = true;
	private Timer timer = new Timer();

	@Override
	public void onDisable() {
		openedChests.clear();
		stole = true;
		super.onDisable();
	}

	@Override
	public void onEnable() {
		openedChests.clear();
		stole = true;
		super.onEnable();
	}
	
	@Override
	public void onEvent(Event e) {
		if(e instanceof EventMotion && e.getType() == EventType.PRE) {
			if(this.chestStealDelay.getValue() != 0) {
				if ((this.mc.thePlayer.openContainer != null) && ((this.mc.thePlayer.openContainer instanceof ContainerChest))) {
					ContainerChest container = (ContainerChest) this.mc.thePlayer.openContainer;
					for (int i = 0; i < container.getLowerChestInventory().getSizeInventory(); i++) {
						if ((container.getLowerChestInventory().getStackInSlot(i) != null) && (timer.hasTimeElapsed((long) chestStealDelay.getValue(), true))) {
							this.mc.playerController.windowClick(container.windowId, i, 0, 1, this.mc.thePlayer);
						}
					}
				}
			}
			if (!stole) {
				return;
			}
			if (this.aura.getValue()) {
				for (Object tile : mc.theWorld.loadedTileEntityList) {
					TileEntity tileEntity = (TileEntity)tile;
					if (!(tileEntity instanceof TileEntityChest)) {
						continue;
					}
					TileEntityChest chestTile = (TileEntityChest) tileEntity;
					if (mc.thePlayer.getDistance(tileEntity.xCoord,tileEntity.yCoord,tileEntity.zCoord) < 3.5) {
						if (mc.currentScreen == null) {
							if (openedChests.contains(tileEntity)) {
								continue;
							}
							stole = false;
							float[] rots = getRotations(tileEntity.xCoord,tileEntity.yCoord,tileEntity.zCoord);
							
							((EventMotion)e).yaw = rots[0];
							((EventMotion)e).pitch = rots[1];
							
							mc.thePlayer.swingItem();
							mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld,
									mc.thePlayer.getCurrentEquippedItem(),
									tileEntity.xCoord,tileEntity.yCoord,tileEntity.zCoord, EnumFacing.getFacingFromVector((float) mc.thePlayer.posX,
											(float) mc.thePlayer.posY, (float) mc.thePlayer.posZ).ordinal(),
									new Vec3(mc.theWorld.getWorldVec3Pool(), tileEntity.xCoord,tileEntity.yCoord,tileEntity.zCoord));
							openedChests.add(chestTile);
							break;
						}
					}
				}
			}
		}
		if(e instanceof EventMotion && e.getType() == EventType.POST) {
			if (mc.currentScreen instanceof GuiChest) {
				GuiChest chest = (GuiChest) mc.currentScreen;
				chest.steal();
				if (chest.isEmpty()) {
					mc.thePlayer.closeScreen();
					stole = true;
				}
			}
		}
		super.onEvent(e);
	}
	
	public float[] getRotations(double x, double y, double z) {
		EaglercraftRandom rand = new EaglercraftRandom(System.currentTimeMillis());
        double deltaX = (x+0.5) - (mc.thePlayer.posX - (mc.thePlayer.posX - mc.thePlayer.lastTickPosX)),
                deltaY = (y+0.5) - (mc.thePlayer.posY - (mc.thePlayer.posY - mc.thePlayer.lastTickPosY)),
                deltaZ = (z+0.5) - (mc.thePlayer.posZ - (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ)),
                distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));
        
        float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ)),
                pitch = (float) -Math.toDegrees(Math.atan(deltaY / distance));

        if (deltaX < 0 && deltaZ < 0) {
            yaw = (float) (90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        } else if (deltaX > 0 && deltaZ < 0) {
            yaw = (float) (-90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        }

        return new float[]{yaw, pitch};
    }
	
}
