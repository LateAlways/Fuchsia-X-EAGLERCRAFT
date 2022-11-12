package net.minecraft.src;

import ghost.Client;
import ghost.events.EventType;
import ghost.events.impl.EventMotion;
import ghost.events.impl.EventUpdate;
import net.minecraft.client.Minecraft;

public class EntityClientPlayerMP extends EntityPlayerSP {
	public NetClientHandler sendQueue;
	private double oldPosX;

	/** Old Minimum Y of the bounding box */
	private double oldMinY;
	private double oldPosY;
	private double oldPosZ;
	private float oldRotationYaw;
	private float oldRotationPitch;

	/** Check if was on ground last update */
	private boolean wasOnGround = false;

	/** should the player stop sneaking? */
	private boolean shouldStopSneaking = false;
	private boolean wasSneaking = false;
	private int field_71168_co = 0;
	
	

	/** has the client player's health been set? */
	private boolean hasSetHealth = false;

	public EntityClientPlayerMP(Minecraft par1Minecraft, World par2World, String par3Session, NetClientHandler par4NetClientHandler) {
		super(par1Minecraft, par2World, par3Session, 0);
		this.sendQueue = par4NetClientHandler;
	}

	/**
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2) {
		return false;
	}

	/**
	 * Heal living entity (param: amount of half-hearts)
	 */
	public void heal(int par1) {
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate() {
		if (this.worldObj.blockExists(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ))) {
			super.onUpdate();
			this.sendMotionUpdates();
		}
	}
	
	public float lastYaw;
	public float lastPitch;

	/**
	 * Send updated motion and position information to the server
	 */
	public void sendMotionUpdates() {
		EventMotion e = new EventMotion();
		/*e.motionX = this.motionX;
		e.motionY = this.motionY;
		e.motionZ = this.motionZ;*/
		e.yaw = this.rotationYaw;
		e.pitch = this.rotationPitch;
		e.onGround = this.onGround;
		e.posX = this.posX;
		e.posY = this.posY;
		e.posZ = this.posZ;
		e.minY = this.boundingBox.minY;
		e.lastYaw = this.rotationYaw;
		e.lastPitch = this.rotationPitch;
		e.setType(EventType.PRE);
		Client.INSTANCE.modManager.onEvent(e);
		boolean var1 = this.isSprinting();

		if (var1 != this.wasSneaking) {
			if (var1) {
				this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 4));
			} else {
				this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 5));
			}

			this.wasSneaking = var1;
		}

		boolean var2 = this.isSneaking();

		if (var2 != this.shouldStopSneaking) {
			if (var2) {
				this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 1));
			} else {
				this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 2));
			}

			this.shouldStopSneaking = var2;
		}

		double var3 = e.posX - this.oldPosX;
		double var5 = e.minY - this.oldMinY;
		double var7 = e.posZ - this.oldPosZ;
		double var9 = (double) (e.yaw - this.oldRotationYaw);
		double var11 = (double) (e.pitch - this.oldRotationPitch);
		boolean var13 = var3 * var3 + var5 * var5 + var7 * var7 > 9.0E-4D || this.field_71168_co >= 20;
		boolean var14 = var9 != 0.0D || var11 != 0.0D;
		
		
		/*mc.thePlayer.rotationPitch = e.pitch;
		mc.thePlayer.rotationYaw = e.yaw;*/
		
		Client.INSTANCE.modManager.onEvent(new EventUpdate().setType(EventType.PRE));
		
		if (this.ridingEntity != null) {
			//this.sendQueue.addToSendQueue(new Packet13PlayerLookMove(this.motionX, -999.0D, -999.0D, this.motionZ, e.yaw, e.pitch, e.onGround));
			this.sendQueue.addToSendQueue(new Packet13PlayerLookMove(e.posX, e.posY, e.minY, e.posZ, e.yaw, e.pitch, e.onGround));
			var13 = false;
		} else if (var13 && var14) {
			this.sendQueue.addToSendQueue(new Packet13PlayerLookMove(e.posX, e.minY, e.posY, e.posZ, e.yaw, e.pitch, e.onGround));
		} else if (var13) {
			this.sendQueue.addToSendQueue(new Packet11PlayerPosition(e.posX, e.minY, e.posY, e.posZ, e.onGround));
		} else if (var14) {
			this.sendQueue.addToSendQueue(new Packet12PlayerLook(e.yaw, e.pitch, e.onGround));
		} else {
			this.sendQueue.addToSendQueue(new Packet10Flying(e.onGround));
		}

		++this.field_71168_co;
		this.wasOnGround = this.onGround;

		if (var13) {
			this.oldPosX = e.posX;
			this.oldMinY = e.minY;
			this.oldPosY = e.posY;
			this.oldPosZ = e.posZ;
			this.field_71168_co = 0;
		}

		if (var14) {
			this.oldRotationYaw = e.yaw;
			this.oldRotationPitch = e.pitch;
		}
		e.yaw = this.rotationYaw;
		e.pitch = this.rotationPitch;
		e.onGround = this.onGround;
		e.posX = this.posX;
		e.posY = this.posY;
		e.posZ = this.posZ;
		e.minY = this.boundingBox.minY;
		e.setType(EventType.POST);
		e.lastYaw = lastYaw;
		e.lastPitch = lastPitch;
		Client.INSTANCE.modManager.onEvent(e);
		lastYaw = e.yaw;
		lastPitch = e.pitch;
		Client.INSTANCE.modManager.onEvent(new EventUpdate().setType(EventType.POST));
	}

	/**
	 * Called when player presses the drop item key
	 */
	public EntityItem dropOneItem(boolean par1) {
		int var2 = par1 ? 3 : 4;
		this.sendQueue.addToSendQueue(new Packet14BlockDig(var2, 0, 0, 0, 0));
		return null;
	}

	/**
	 * Joins the passed in entity item with the world. Args: entityItem
	 */
	protected void joinEntityItemWithWorld(EntityItem par1EntityItem) {
	}

	/**
	 * Sends a chat message from the player. Args: chatMessage
	 */
	public void sendChatMessage(String par1Str) {
		this.sendQueue.addToSendQueue(new Packet3Chat(par1Str));
	}

	/**
	 * Swings the item the player is holding.
	 */
	public void swingItem() {
		super.swingItem();
		this.sendQueue.addToSendQueue(new Packet18Animation(this, 1));
	}

	public void respawnPlayer() {
		this.sendQueue.addToSendQueue(new Packet205ClientCommand(1));
	}

	/**
	 * Deals damage to the entity. If its a EntityPlayer then will take damage from
	 * the armor first and then health second with the reduced value. Args:
	 * damageAmount
	 */
	protected void damageEntity(DamageSource par1DamageSource, int par2) {
		if (!this.isEntityInvulnerable()) {
			this.setEntityHealth(this.getHealth() - par2);
		}
	}

	/**
	 * sets current screen to null (used on escape buttons of GUIs)
	 */
	public void closeScreen() {
		this.sendQueue.addToSendQueue(new Packet101CloseWindow(this.openContainer.windowId));
		this.func_92015_f();
	}

	public void func_92015_f() {
		this.inventory.setItemStack((ItemStack) null);
		super.closeScreen();
	}

	/**
	 * Updates health locally.
	 */
	public void setHealth(int par1) {
		if (this.hasSetHealth) {
			super.setHealth(par1);
		} else {
			this.setEntityHealth(par1);
			this.hasSetHealth = true;
		}
	}

	/**
	 * Adds a value to a statistic field.
	 */
	public void addStat(StatBase par1StatBase, int par2) {
		if (par1StatBase != null) {
			if (par1StatBase.isIndependent) {
				super.addStat(par1StatBase, par2);
			}
		}
	}

	/**
	 * Used by NetClientHandler.handleStatistic
	 */
	public void incrementStat(StatBase par1StatBase, int par2) {
		if (par1StatBase != null) {
			if (!par1StatBase.isIndependent) {
				super.addStat(par1StatBase, par2);
			}
		}
	}

	/**
	 * Sends the player's abilities to the server (if there is one).
	 */
	public void sendPlayerAbilities() {
		this.sendQueue.addToSendQueue(new Packet202PlayerAbilities(this.capabilities));
	}

	public boolean func_71066_bF() {
		return true;
	}
}
