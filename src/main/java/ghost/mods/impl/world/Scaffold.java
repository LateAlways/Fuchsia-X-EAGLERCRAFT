package ghost.mods.impl.world;

import ghost.events.Event;
import ghost.events.impl.EventMotion;
import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;
import ghost.mods.essential.settings.BooleanSetting;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.Packet103SetSlot;
import net.minecraft.src.Vec3;

public class Scaffold extends Mod {

	public BooleanSetting switchSlot = new BooleanSetting("Switch slot", "Switches to the slot number or spoofs to the server.", true);
	
	public Scaffold() {
		super("SafeWalk", "Prevents you from falling like if you were sneaking.", Category.WORLD, true);
	}
	int offsetX = 0, offsetZ = 0;
	public static int X;
	public static int Y;
	public static int Z;
	public int oldslot = -1;
	
	public void switchToBlock() {
		int toswitch = -1;
		for(int i = 0; i < mc.thePlayer.inventory.getHotbarSize(); i++) {
			if(mc.thePlayer.inventory.getStackInSlot(i) != null && mc.thePlayer.inventory.getStackInSlot(i).getItem() != null && mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBlock) {
				toswitch = i;
			}
		}
		if(toswitch != -1) {
			oldslot = mc.thePlayer.inventory.currentItem;

			if(switchSlot.getValue()) {
				mc.thePlayer.inventory.currentItem = oldslot;
			} else {
				mc.getNetHandler().addToSendQueue(new Packet103SetSlot(0, toswitch, mc.thePlayer.inventory.getStackInSlot(toswitch)));
			}
		}
	}
	
	@Override
	public void onDisable() {
		if(oldslot != -1) {
			mc.thePlayer.inventory.currentItem = oldslot;
			oldslot = -1;
		}
	}
	
	public void onEvent(Event e) {
		/*if(e instanceof EventMotion) {
			offsets();
			
			X = (int)mc.thePlayer.posX;
			Y = (int)mc.thePlayer.posY;
			Z = (int)mc.thePlayer.posZ;
			double posX = mc.thePlayer.posX;
			double posZ = mc.thePlayer.posZ;
			double decX = posX - X;
			double decZ = posZ - Z;		
			if(decX < 0.3 || decX > 0.7 || decX < -0.7 || decX > -0.3){
				if(mc.thePlayer.motionX > 0.02) {
					if(mc.theWorld.getBlockMaterial(X+offsetX+1, (int)mc.thePlayer.posY - 2, Z+offsetZ).isReplaceable()) {
						switchToBlock();
						int[] values = getDir(new Vec3(Vec3.fakePool,(double)X+offsetX+1, mc.thePlayer.posY - 2, (double)Z+offsetZ));
						
						mc.thePlayer.swingItem();
						mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), values[0], values[1], values[2], values[3], new Vec3(Vec3.fakePool, values[0], values[1], values[2]));
					}
				}
				if(mc.thePlayer.motionX < -0.02) {
					if(mc.theWorld.getBlockMaterial(X+offsetX-1, (int)mc.thePlayer.posY - 2, Z+offsetZ).isReplaceable()) {
						switchToBlock();
						int[] values = getDir(new Vec3(Vec3.fakePool,(double)X+offsetX-1, mc.thePlayer.posY - 2, (double)Z+offsetZ));
						((EventMotion) e).setYaw(90);
						((EventMotion) e).setPitch(45);
						mc.thePlayer.swingItem();
						mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), values[0], values[1], values[2], values[3], new Vec3(Vec3.fakePool, values[0], values[1], values[2]));
					}
				}
			}
			if(decZ < 0.3 || decZ > 0.7 || decZ < -0.7 || decZ > -0.3) {
				if(mc.thePlayer.motionZ > 0.02) {
					if(mc.theWorld.getBlockMaterial(X+offsetX, (int)mc.thePlayer.posY - 2, Z+offsetZ+1).isReplaceable()) {
						switchToBlock();
						int[] values = getDir(new Vec3(Vec3.fakePool,(double)X+offsetX, mc.thePlayer.posY - 2, (double)Z+offsetZ+1));
						((EventMotion) e).setYaw(0);
						((EventMotion) e).setPitch(45);
						mc.thePlayer.swingItem();
						mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), values[0], values[1], values[2], values[3], new Vec3(Vec3.fakePool, values[0], values[1], values[2]));
					}
				}
				if(mc.thePlayer.motionZ < -0.02) {
					if(mc.theWorld.getBlockMaterial(X+offsetX, (int)mc.thePlayer.posY - 2, Z+offsetZ-1).isReplaceable()) {
						switchToBlock();
						int[] values = getDir(new Vec3(Vec3.fakePool,(double)X+offsetX, mc.thePlayer.posY - 2, (double)Z+offsetZ-1));
						((EventMotion) e).setYaw(180);
						((EventMotion) e).setPitch(45);
						mc.thePlayer.swingItem();
						mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), values[0], values[1], values[2], values[3], new Vec3(Vec3.fakePool, values[0], values[1], values[2]));
					}
				}
			}
			
			if(mc.theWorld.getBlockMaterial(X+offsetX, Y-2, Z+offsetZ).isReplaceable()) {
				switchToBlock();
				int[] values = getDir(new Vec3(Vec3.fakePool,(double)X+offsetX, mc.thePlayer.posY - 2, (double)Z+offsetZ));
				((EventMotion) e).setYaw(0);
				((EventMotion) e).setPitch(90);
				mc.thePlayer.swingItem();
				mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), values[0], values[1], values[2], values[3], new Vec3(Vec3.fakePool, values[0], values[1], values[2]));
			}
		}	*/
	}
	
	public void offsets() {
		if(mc.thePlayer.posX < 0 && mc.thePlayer.posZ < 0) {
			offsetX = -1;
			offsetZ = -1;	
		}
		if(mc.thePlayer.posX > 0 && mc.thePlayer.posZ > 0) {
			offsetX = 0;
			offsetZ = 0;
		}
		if(mc.thePlayer.posX > 0 && mc.thePlayer.posZ < 0) {
			offsetX = 0;
			offsetZ = -1;
		}
		if(mc.thePlayer.posX < 0 && mc.thePlayer.posZ > 0) {
			offsetX = -1;
			offsetZ =  0;
		}
	}	
	
	
	public int[] getDir(Vec3 blockpos) {
		if(!mc.theWorld.isAirBlock((int)blockpos.xCoord, (int)blockpos.yCoord-1, (int)blockpos.zCoord)) {
			int[] values = {(int)blockpos.xCoord+0,(int)blockpos.yCoord+-1,(int)blockpos.zCoord+0,1};
			return values;
 		}
		if(!mc.theWorld.isAirBlock((int)blockpos.xCoord+1, (int)blockpos.yCoord, (int)blockpos.zCoord)) {
			int[] values = {(int)blockpos.xCoord+1,(int)blockpos.yCoord+0,(int)blockpos.zCoord+0,4};
			return values;
 		}
		if(!mc.theWorld.isAirBlock((int)blockpos.xCoord-1, (int)blockpos.yCoord, (int)blockpos.zCoord)) {
			int[] values = {(int)blockpos.xCoord-1,(int)blockpos.yCoord+0,(int)blockpos.zCoord+0,5};
			return values;
 		}
		if(!mc.theWorld.isAirBlock((int)blockpos.xCoord, (int)blockpos.yCoord, (int)blockpos.zCoord+1)) {
			int[] values = {(int)blockpos.xCoord+0,(int)blockpos.yCoord+0,(int)blockpos.zCoord+1,2};
			return values;
 		}
		if(!mc.theWorld.isAirBlock((int)blockpos.xCoord, (int)blockpos.yCoord, (int)blockpos.zCoord-1)) {
			int[] values = {(int)blockpos.xCoord+0,(int)blockpos.yCoord+0,(int)blockpos.zCoord-1,3};
			return values;
 		}
		if(!mc.theWorld.isAirBlock((int)blockpos.xCoord, (int)blockpos.yCoord - 1, (int)blockpos.zCoord)) {
			
			int[] values = {(int)blockpos.xCoord+0,(int)blockpos.yCoord-1,(int)blockpos.zCoord, 0};
			return values;
 		}
		
		int[] values = {0,0,0,0};
		return values;
		
	}
	
	/*public int[] add(double[] original, double x, double y, double z) {
		original[0] += x;
		original[1] += y;
		original[2] += z;
		
		return new int[] { (int)original[0], (int)original[1], (int) original[2] };
	}
	
	@Override
	public void onUpdate() {
		if(this.isEnabled()) {
			Entity p = mc.thePlayer;
			double[] bp = {p.posX, p.boundingBox.minY, p.posZ};
			
			if(valid(add(bp,0, -2, 0)))
				place(add(bp,0, -1, 0), EnumFacing.UP);
			
			else if (valid(add(bp,-1, -1, 0)))
				
				place(add(bp,0, -1, 0), EnumFacing.EAST);
			
			else if (valid(add(bp,1, -1, 0)))
				
				place(add(bp,0, -1, -1), EnumFacing.WEST);
			
			else if (valid(add(bp,0, -1, -1)))
				
				place(add(bp,0, -1, 0), EnumFacing.SOUTH);
			
			else if (valid(add(bp,0, -1, 1)))
				
				place(add(bp,0, -1, 0), EnumFacing.NORTH);
			
			else if(valid(add(bp,0, -1, 1))) {
				
				if(valid(add(bp,0, -1, 1)))
					
					place(add(bp,0, -1, 1), EnumFacing.NORTH);
				place(add(bp,1, -1, 1), EnumFacing.EAST);
			}else if (valid(add(bp,-1, -1, 1))) {
				if(valid(add(bp,-1, -1, 0)))
					place(add(bp,0, -1, 1), EnumFacing.WEST);
				place(add(bp,-1, -1, 1),EnumFacing.SOUTH);
			}else if (valid(add(bp,-1, -1, -1))) {
				if(valid(add(bp,0, -1, -1)))
					place(add(bp,0, -1, 1), EnumFacing.SOUTH);
				place(add(bp,-1, -1, 1), EnumFacing.WEST);
			}else if (valid(add(bp,1, -1, -1))) {
				if(valid(add(bp,1, -1, 0)))
					place(add(bp,1, -1, 0), EnumFacing.EAST);
				place(add(bp,1, -1, -1), EnumFacing.NORTH);
			}
		}
	}
	
	public int getFacing(EnumFacing face) {
		for(int i = 0; i < EnumFacing.values().length; i++) {
			if(EnumFacing.values()[i] == face) {
				return i;
			}
		}
		return (int) -1;
	}
	
	void place(int[] p, EnumFacing f) {
		if(f == EnumFacing.UP)
			p[1] = p[1]-1;
		else if(f == EnumFacing.NORTH)
			p[2] = p[2]+1;
		else if(f == EnumFacing.EAST)
			p[0] = p[0]-1;
		else if(f == EnumFacing.SOUTH)
			p[2] = p[2]-1;
		else if(f == EnumFacing.WEST)
			p[0] = p[0]+1;
		
		EntityPlayer _p = mc.thePlayer;
		
		if(_p.getHeldItem() != null && _p.getHeldItem().getItem() instanceof ItemBlock) {
			_p.swingItem();
		mc.playerController.onPlayerRightClick(_p, mc.theWorld.provider.worldObj, _p.inventory.getCurrentItem(), p[0], p[1], p[2], getFacing(f), new Vec3(Vec3.fakePool, 0.5, 0.5, 0.5));
		double x = p[0] + 0.25 - _p.posX;
		double z = p[2] + 0.25 - _p.posZ;
		double y = p[1] + 0.25 - _p.posY;
		double distance = MathHelper.sqrt_double(x * x + z * z);
		float yaw = (float) (Math.atan2(z, x) * 180 / Math.PI - 90);
		float pitch = (float) - (Math.atan2(y, distance) * 180 /Math.PI);
		mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(_p.posX, _p.boundingBox.minY, _p.posY, _p.posZ, yaw, pitch, _p.onGround));
		}
	}
	
	boolean valid(int[] p) {
		Block b = Block.getBlockById(mc.theWorld.getBlockId(p[0], p[1], p[2]));
		
		return b.blockMaterial != Material.air;
	}*/
	
}
