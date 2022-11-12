package ghost.mods.impl.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import ghost.Client;
import ghost.events.Event;
import ghost.events.EventType;
import ghost.events.impl.EventAttack;
import ghost.events.impl.EventDamaged;
import ghost.events.impl.EventMotion;
import ghost.events.impl.EventProcessPacket;
import ghost.events.impl.EventSendPacket;
import ghost.events.impl.EventUpdate;
import ghost.gui.ClickGUI;
import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;
import ghost.mods.essential.RenderModule;
import ghost.mods.essential.settings.BooleanSetting;
import ghost.mods.essential.settings.NumberSetting;
import ghost.utils.Hit;
import ghost.utils.HitRatio;
import ghost.utils.RenderUtils;
import ghost.utils.RotationUtils;
import ghost.utils.Timer;
import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.EaglercraftRandom;
import net.lax1dude.eaglercraft.adapter.Tessellator;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItemFrame;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumMovingObjectType;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiInventory;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.MathHelper;
import net.minecraft.src.OpenGlHelper;
import net.minecraft.src.Packet106Transaction;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Packet19EntityAction;
import net.minecraft.src.Packet7UseEntity;
import net.minecraft.src.Packet8UpdateHealth;
import net.minecraft.src.Potion;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderManager;

public class KillAura extends RenderModule {

	public KillAura() {
		super("KillAura", "Kills enemy that enter in a specific range.", Category.COMBAT, 10,10,200,50,true);
		addSetting(mincps,maxcps, noswing, range, rotate,antibot);
	}
	
	public Timer clickTimer = new Timer();
	
	public NumberSetting mincps = new NumberSetting("Minimum CPS", "Minimum Clicks per second.", 15, 1, 20,1,1);
	public NumberSetting maxcps = new NumberSetting("Maximum CPS", "Maximum Clicks per second.", 15, 1, 20,1,1);
	public int lastCps = -1;
	public BooleanSetting noswing = new BooleanSetting("NoSwing", "Disable swing animation", false);
	public BooleanSetting antibot = new BooleanSetting("AntiBot", "Prevents you from attacking bots.", false);
	public NumberSetting range = new NumberSetting("Range","KillAura range", 4,1,6,0.1f,0.1f);
	public BooleanSetting rotate = new BooleanSetting("Rotate", "Rotate your head towards the enemy", true);
	
	public Entity lookingAt = null;
	
	public void drawEntity(EntityLiving par0Minecraft, int par1, int par2, int par3, float par4, float par5) {
		EaglerAdapter.glEnable(EaglerAdapter.GL_COLOR_MATERIAL);
		EaglerAdapter.glPushMatrix();
		EaglerAdapter.glTranslatef((float) par1, (float) par2, 50.0F);
		EaglerAdapter.glScalef((float) (-par3), (float) par3, (float) par3);
		EaglerAdapter.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		float var6 = par0Minecraft.renderYawOffset;
		float var7 = par0Minecraft.rotationYaw;
		float var8 = par0Minecraft.rotationPitch;
		EaglerAdapter.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		EaglerAdapter.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
		EaglerAdapter.glRotatef(-((float) Math.atan((double) (par5 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
		/*par0Minecraft.renderYawOffset = (float) Math.atan((double) (par4 / 40.0F)) * 20.0F;
		par0Minecraft.rotationYaw = (float) Math.atan((double) (par4 / 40.0F)) * 40.0F;
		par0Minecraft.rotationPitch = -((float) Math.atan((double) (par5 / 40.0F))) * 20.0F;
		par0Minecraft.rotationYawHead = par0Minecraft.rotationYaw;*/
		EaglerAdapter.glTranslatef(0.0F, par0Minecraft.yOffset, 0.0F);
		RenderManager.instance.playerViewY = 0.0F;
		RenderManager.instance.renderEntityWithPosYaw(par0Minecraft, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		//par0Minecraft.renderYawOffset = var6;
		/*par0Minecraft.rotationYaw = var7;
		par0Minecraft.rotationPitch = var8;*/
		EaglerAdapter.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		EaglerAdapter.glDisable(EaglerAdapter.GL_RESCALE_NORMAL);
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		EaglerAdapter.glDisable(EaglerAdapter.GL_TEXTURE_2D);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}
	
	public HitRatio hitRatio = new HitRatio();
	public HitRatio hitRatioLookingAt = new HitRatio();
	 
	@Override
	public void draw() {
		if(lookingAt != null && !lookingAt.isDead) {
			hitRatio.update();
			hitRatioLookingAt.update();
			this.setWidth(160);
			RenderUtils.drawRoundedRect(this.x, this.y+1, width, height-3, 3, 0xe6000000);
			EaglerAdapter.glColor4f((float)(mc.thePlayer.getDistanceSqToEntity(lookingAt)/40), (float)(1-mc.thePlayer.getDistanceSqToEntity(lookingAt)/40f), 0f, 1f);
			RenderUtils.drawRoundedRect((int)(x+width/2/2), y+height/2-11/2, 11, 9, 2);
			EaglerAdapter.glColor4f(1, 1, 1, 1);
			EaglerAdapter.glColor4f((float)(mc.thePlayer.getDistanceSqToEntity(lookingAt)/40), (float)(mc.thePlayer.getDistanceSqToEntity(lookingAt)/40f), (float)(mc.thePlayer.getDistanceSqToEntity(lookingAt)/40f), 1f);
			Minecraft.getMinecraft().fontRenderer.drawString(String.valueOf(MathHelper.floor_float(mc.thePlayer.getDistanceToEntity(lookingAt))), (int)(x+width/2/2)+11/2-Minecraft.getMinecraft().fontRenderer.getStringWidth(String.valueOf(MathHelper.floor_float(mc.thePlayer.getDistanceToEntity(lookingAt))))/2+1, y+height/2-11/2+9/2-Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT/2+1/2);
			EaglerAdapter.glColor4f(1, 1, 1, 1);
			Minecraft.getMinecraft().fontRenderer.drawString(lookingAt.getEntityName(), x+5, y+5, -1);
			this.drawEntity((EntityLiving) lookingAt, x+width/2/2/2, y+height/2+15, 10, 10f, 10f);
			if(lookingAt instanceof EntityPlayer) {
				float win = 0;
				if((float)((EntityLiving)lookingAt).getHealth()/20f > (float)mc.thePlayer.getHealth()/20f) {
					win = 0f;
				}
				if((float)((EntityLiving)lookingAt).getHealth()/20f < (float)mc.thePlayer.getHealth()/20f) {
					win = 1f;
				}
				if((float)((EntityLiving)lookingAt).getHealth()/20f == (float)mc.thePlayer.getHealth()/20f) {
					win = 0.5f;
				}
				
				if(hitRatio.getCPS() > hitRatioLookingAt.getCPS()) {
					win += 1;
				}
				if(hitRatio.getCPS() < hitRatioLookingAt.getCPS()) {
					win += 0;
				}
				if(hitRatio.getCPS() == hitRatioLookingAt.getCPS()) {
					win += 0.5f;
				}
				String winning = "";
				if(win < 1) {
					winning = "No";
				}
				if(win > 1) {
					winning = "Yes";
				}
				if(win == 1) {
					winning = "Maybe";
				}
				//RenderUtils.drawRectOutline((int)(x+width/2/2)+11/2-Minecraft.getMinecraft().fontRenderer.getStringWidth(String.valueOf(MathHelper.floor_float(mc.thePlayer.getDistanceToEntity(lookingAt)))), (y+height/2-11/2+9/2-Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT/2+1/2)+11, (int)(x+width/2.5)+11/2-Minecraft.getMinecraft().fontRenderer.getStringWidth(String.valueOf(MathHelper.floor_float(mc.thePlayer.getDistanceToEntity(lookingAt))))/2+1+11+width/2-5, (y+height/2-11/2+9/2-Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT/2+1/2)+20, -1);
				//RenderUtils.drawChromaRectangle(1+(int)(x+width/2/2)+11/2-Minecraft.getMinecraft().fontRenderer.getStringWidth(String.valueOf(MathHelper.floor_float(mc.thePlayer.getDistanceToEntity(lookingAt)))), 1+(y+height/2-11/2+9/2-Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT/2+1/2)+11, 1+(int)(x+width/2/2)+11/2-Minecraft.getMinecraft().fontRenderer.getStringWidth(String.valueOf(MathHelper.floor_float(mc.thePlayer.getDistanceToEntity(lookingAt))))+(int)(112f*MathHelper.clamp_float(((float)((EntityLiving)lookingAt).getHealth()/(float)20f),0,1)), -1+(y+height/2-11/2+9/2-Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT/2+1/2)+20);
				Minecraft.getMinecraft().fontRenderer.drawString("\u2665 "+((EntityLiving)lookingAt).getHealth()+" Winning?: "+winning, (int)(x+width/2/2)+11/2-Minecraft.getMinecraft().fontRenderer.getStringWidth(String.valueOf(MathHelper.floor_float(mc.thePlayer.getDistanceToEntity(lookingAt))))/2+1+11, (y+height/2-11/2+9/2-Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT/2+1/2), -1);
			} else {
				Minecraft.getMinecraft().fontRenderer.drawString("\u2665 "+((EntityLiving)lookingAt).getHealth(), (int)(x+width/2/2)+11/2-Minecraft.getMinecraft().fontRenderer.getStringWidth(String.valueOf(MathHelper.floor_float(mc.thePlayer.getDistanceToEntity(lookingAt))))/2+1+11, (y+height/2-11/2+9/2-Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT/2+1/2), -1);
			}
		}
		super.draw();
	}
		
	
	@Override
	public void onUpdate() {
		try {
			
		} catch(Exception e) {}
	}
	
	public Timer lastSent = new Timer();
	
	public double getJumpMotion(float motionY) {
        final Potion potion = Potion.jump;

        if (mc.thePlayer.isPotionActive(potion)) {
            final int amplifier = mc.thePlayer.getActivePotionEffect(potion).getAmplifier();
            motionY += (amplifier + 1) * 0.1F;
        }
        return motionY;
    }
	
	public Timer attackTimer = new Timer();

	public boolean hasPreRun = false;
	
	public int counter = 0;
	
	public boolean attacking = false;
	
	public double lastPosX,lastPosY,lastPosZ;
	public float lastYaw, lastPitch;
	
	public boolean currWaitingCooldown = false;
	public int targetcps = -1;
	
	public Timer packetflyingtimer = new Timer();
	
	@Override
	public void onEvent(Event e) {
		if(e instanceof EventAttack) {
			hitRatio.add(new Hit(true));
		}
		if(e instanceof EventDamaged) {
			hitRatioLookingAt.add(new Hit(false));
		}
		if(!mc.thePlayer.isDead && e instanceof EventMotion && e.type == EventType.PRE) {
			if(getClosestEntity() != null && (this.antibot.getValue() ? !isBot(getClosestEntity()) : true) && mc.thePlayer.getDistanceToEntity(getClosestEntity()) < range.getValue()+2 && !getClosestEntity().isInvisible() && !getClosestEntity().isEntityInvulnerable() && !getClosestEntity().isInvisible()) {
				lookingAt = getClosestEntity();
				hitRatioLookingAt.hits.clear();
				((EventMotion) e).yaw = getRotations(lookingAt, ((EventMotion)e))[0];
				((EventMotion) e).pitch = getRotations(lookingAt,((EventMotion)e))[1];
				hasPreRun = true;
			} else {
				lookingAt = null;
				hitRatioLookingAt.hits.clear();
			}
		}
		
		if(e instanceof EventSendPacket) {
			if(((EventSendPacket) e).packet instanceof Packet10Flying) {
				if(!packetflyingtimer.hasTimeElapsed(2, true)) {
					e.cancelled = true;
				}
				Packet10Flying p = (Packet10Flying)((EventSendPacket) e).packet;
				if(counter >= 20) {
					counter = 0;
					if(lookingAt != null) {
						if(lastPitch != p.pitch)
						p.pitch = getRotations(lookingAt)[1];
						if(lastYaw != p.yaw)
						p.yaw = getRotations(lookingAt)[0];
					}
					if(lastPosX != p.xPosition)
					p.xPosition = mc.thePlayer.posX;
					if(lastPosY != p.yPosition)
					p.yPosition = mc.thePlayer.boundingBox.minY;
					if(lastPosZ != p.zPosition)
					p.zPosition = mc.thePlayer.posZ;
					if(p.xPosition != 0 || p.yPosition != 0 || p.zPosition != 0 || p.yaw != 0 || p.pitch != 0)
					p.moving = true;
					lastPosX = p.xPosition;
					lastPosY = p.yPosition;
					lastPosZ = p.zPosition;
					lastYaw = p.yaw;
					lastPitch = p.pitch;
				} else {
					++counter;
				}
				/*System.out.println(p.moving);
				System.out.println(p.onGround);
				System.out.println(p.xPosition);
				System.out.println(p.yPosition);
				System.out.println(p.zPosition);
				System.out.println(p.yaw);
				System.out.println(p.pitch);*/
			}
			if((((((EventSendPacket) e).packet instanceof Packet14BlockDig) || (((EventSendPacket)e).packet instanceof Packet15Place)))  && lookingAt != null) {
				e.cancelled = true;
			}
			//System.out.println(System.currentTimeMillis() + "        "+  ((EventSendPacket) e).packet + "           "+e.cancelled);
		}
		if(!mc.thePlayer.isDead && e instanceof EventMotion && e.type == EventType.POST) {
			EaglercraftRandom rand = new EaglercraftRandom(System.currentTimeMillis());
			
			if(!currWaitingCooldown) {
				targetcps = (rand.nextInt((int)maxcps.getValue()+1 - (int)mincps.getValue()) + (int)mincps.getValue());
				currWaitingCooldown = true;
			}
			if(currWaitingCooldown && hasPreRun && lookingAt != null && !lookingAt.isDead && mc.thePlayer.getDistanceToEntity(lookingAt) < range.getValue() && !lookingAt.isInvisible() && clickTimer.hasTimeElapsed((long) (1000/targetcps), true) && !mc.thePlayer.isUsingItem()) {
				attacking = true;
				if(!noswing.getValue())
				mc.thePlayer.swingItem();
				else
					mc.thePlayer.sendQueue.addToSendQueue(new Packet18Animation(mc.thePlayer, 1));
				mc.playerController.attackEntity(mc.thePlayer, lookingAt);
				System.out.println(lookingAt.entityId + "      "+lookingAt.getEntityName());
				attackTimer.reset();
				attacking = false;
				hasPreRun = false;
				currWaitingCooldown = false;
			}
		}
		super.onEvent(e);
	}
	
	public List<String> names = new ArrayList<>();

	public EntityLiving getClosestEntity() {
		
		List<Entity> entitylist = mc.theWorld.getLoadedEntityList();
		EntityLiving closestEntity = null;
		float closest = -1;
		for(Entity e : entitylist) {
			
			if(!(e instanceof EntityItemFrame) && closest == -1 && (e instanceof EntityLiving) && e != mc.thePlayer) {
				closest = mc.thePlayer.getDistanceToEntity(e);
				closestEntity = (EntityLiving) e;
				continue;
			}
			if((e instanceof EntityLiving) && mc.thePlayer.getDistanceToEntity(e) < closest &&/* (e instanceof EntityPlayer) &&*/ !e.isDead && ((EntityLiving)e).health > 0 && e != mc.thePlayer) {
				closest = mc.thePlayer.getDistanceToEntity(e);
				closestEntity = (EntityLiving) e;
			}
		}
		return closestEntity;
	}
	
	public boolean isBot(Entity e) {
		if(antibot.getValue()) {
			if(e.ticksExisted < 50) {
				return true;
			}
		}
		return false;
	}
	
	public float[] getRotations(Entity e) {
		EaglercraftRandom rand = new EaglercraftRandom(System.currentTimeMillis());
        double deltaX = (e.posX - (e.posX - e.lastTickPosX)) - (mc.thePlayer.posX - (mc.thePlayer.posX - mc.thePlayer.lastTickPosX)),
                deltaY = (e.posY - (e.posY - e.lastTickPosY) + e.getEyeHeight()-0.4) - (mc.thePlayer.posY - (mc.thePlayer.posY - mc.thePlayer.lastTickPosY)),
                deltaZ = (e.posZ - (e.posZ - e.lastTickPosZ)) - (mc.thePlayer.posZ - (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ)),
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
	
	public float[] getRotations(Entity e, EventMotion event){
        if(event.getLastYaw() != -1 && event.getLastPitch() != -1) {
        	float[] targetYaw = RotationUtils.getRotations(e);
            EaglercraftRandom rand = new EaglercraftRandom();
            float yaw = 0;
            float speed = (float) (rand.nextInt(22-15)+15)/10;
            float yawDifference = event.getLastYaw() - targetYaw[0];
            yaw = event.getLastYaw() - (yawDifference / speed);
            float pitch = 0;
            float pitchDifference = event.getLastPitch() - targetYaw[1];
            pitch = event.getLastPitch() - (pitchDifference / speed);
            return new float[]{yaw, pitch};
        }
        return null;
    }
	
}
