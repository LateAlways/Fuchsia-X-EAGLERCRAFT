package ghost.mods.impl.movement;

import java.util.ArrayList;
import java.util.List;

import ghost.events.Event;
import ghost.events.EventType;
import ghost.events.impl.EventMotion;
import ghost.events.impl.EventProcessPacket;
import ghost.events.impl.EventSendPacket;
import ghost.events.impl.EventSetPosition;
import ghost.events.impl.EventTick;
import ghost.events.impl.IsPlayerInWaterEvent;
import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;
import ghost.mods.essential.settings.BooleanSetting;
import ghost.mods.essential.settings.ModeSetting;
import ghost.mods.essential.settings.NumberSetting;
import ghost.mods.essential.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Entity;
import net.minecraft.src.GuiDownloadTerrain;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Vec3;

public class Fly extends Mod {

	public boolean shouldFake = false;
	public double posX;
	public double posY;
	public double minY;
	public double posZ;
	public boolean onGround = false;
	
	/*
	 * XULU
	*/
    private final NumberSetting cooldown = new NumberSetting("Cooldown", "", 0f,0f,10f,1f,1f);
    private final NumberSetting fallSpeed = new NumberSetting("FallSpeed", "", 0.005f,0.0f,10.0f,0.001f,0.001f);
    private final NumberSetting upSpeed = new NumberSetting("UpSpeed","", 0.05f,0.0f,10f,0.01f,0.01f);
    private float counter;
    int j;
    
	/*
	 * WurstPlus
	 */
	public NumberSetting speed = new NumberSetting("Speed","",0.1f,0.0f,5.0f,0.1f,0.1f);
	public BooleanSetting nokick = new BooleanSetting("NoKick", "", true);
	public List<Packet> packets = new ArrayList<Packet>();
	private int teleportId;
	
	public Fly() {
		super("Fly", "Allows you to fly.", Category.MOVEMENT);
		addSetting(mode,cooldown,fallSpeed,upSpeed,speed,nokick);
		mode.onChange();
		this.counter = 0.0f;
	}
	
	@Override
	public void onEvent(Event e) {
		if(e instanceof EventMotion && e.getType() == EventType.PRE) {
			EventMotion ev = (EventMotion) e;
			if(shouldFake == true) {
				ev.posX = posX;
				ev.posY = posY;
				ev.minY = minY;
				ev.posZ = posZ;
				ev.onGround = onGround;
			}
		}
		if(mode.getValue() == "WurstPlus") {
			if(e instanceof EventSendPacket) {
				if(!(((EventSendPacket)e).packet instanceof Packet11PlayerPosition)) {
					e.cancelled = true;
				}
				if(this.packets.contains(((EventSendPacket)e).packet)) {
					this.packets.remove(((EventSendPacket)e).packet);
				} else {
					e.cancelled = true;
				}
			}
			Object o10;
			if(e instanceof EventProcessPacket) {
				if(((EventProcessPacket)e).packet instanceof Packet13PlayerLookMove) {
					o10 = ((EventProcessPacket)e).packet;
					if(Minecraft.getMinecraft().thePlayer.isEntityAlive()) {
						if(!(Minecraft.getMinecraft().currentScreen instanceof GuiDownloadTerrain)) {
							if(this.teleportId <= 0) {
							} else {
								e.cancelled = true;
							}
						}
					}
				}
			}
		}
		if(mode.getValue() == "Xulu") {
			if(e instanceof EventTick && e.getType() == EventType.POST) {
				if(mc.thePlayer == null) return;
				if(this.counter < 1.0f || mc.thePlayer.onGround) {
					this.counter += this.cooldown.getValue();
					posX = mc.thePlayer.posX;
					posY = mc.thePlayer.posY-0.03;
					minY = mc.thePlayer.boundingBox.minY-0.03;
					posZ = mc.thePlayer.posZ;
					onGround = mc.thePlayer.onGround;
					shouldFake = true;
					++this.j;
				} else {
					shouldFake = false;
					--this.counter;
				}
			}
			if(mc.gameSettings.keyBindJump.isPressed()) {
				mc.thePlayer.motionY = this.upSpeed.getValue();
			} else if(!mc.thePlayer.onGround) {
				mc.thePlayer.motionY = -this.fallSpeed.getValue();
			}
			super.onEvent(e);
		}
		super.onEvent(e);
	}
	
	@Override
	public void onDisable() {
		shouldFake = false;
		if(mode.getValue() == "Vanilla")
		mc.thePlayer.capabilities.isFlying = false;
		super.onDisable();
	}
	
	@Override
	public void onEnable() {
		this.j = 0;
		if(mode.getValue() == "WurstPlus") {
			if(mc.theWorld != null) {
				this.teleportId = 0;
				this.packets.clear();
				final Packet bounds = new Packet11PlayerPosition(mc.thePlayer.posX, 0.0, mc.thePlayer.boundingBox.minY - mc.thePlayer.posY, mc.thePlayer.posZ,mc.thePlayer.onGround);
				this.packets.add(bounds);
				mc.thePlayer.sendQueue.addToSendQueue(bounds);
			}
		}
	}
	
	@Override
	public void onUpdate() {
		if(mode.getValue() == "WurstPlus") {
			double[] ySpeed = { 0.0 };
	        double[] ySpeed2 = { 0.0 };
	        double[] n = { 0.0 };
	        double[][] directionalSpeed = new double[1][1];
	        int[] i = { 0 };
	        int[] j = { 0 };
	        int[] k = { 0 };
	        Object o;
	        Object o2;
	        Object o3;
	        Object o4;
	        Object o5;
	        Object o6;
	        int n2;
	        Object o7;
	        int n3;
	        Object o8;
	        int n4;
			if(this.teleportId <= 0) {
				o = new Packet11PlayerPosition(mc.thePlayer.posX, 0.0, mc.thePlayer.boundingBox.minY - mc.thePlayer.posY, mc.thePlayer.posZ,mc.thePlayer.onGround);
				this.packets.add((Packet) o);
				mc.thePlayer.sendQueue.addToSendQueue((Packet) o);
				return;
			} else {
				mc.thePlayer.setVelocity(0.0, 0.0, 0.0);
				if(mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.boundingBox.expand(-0.0625, 0.0, -0.0625)).isEmpty()) {
					if(mc.gameSettings.keyBindJump.isPressed()) {
						if(this.nokick.getValue()) {
							o3 = (mc.thePlayer.ticksExisted % 20 == 0) ? -0.03999999910593033 : 0.06199999898672104;
						} else {
                            o3 = 0.06199999898672104;
                        }
					} else if (mc.gameSettings.keyBindSneak.isPressed()) {
                        o3 = -0.062;
                    } else {
                    	if (mc.theWorld.getCollidingBoundingBoxes((Entity)mc.thePlayer, mc.thePlayer.boundingBox.expand(-0.0625, -0.0625, -0.0625)).isEmpty()) {
                            if (mc.thePlayer.ticksExisted % 4 == 0) {
                                o4 = (double)(this.nokick.getValue() ? -0.04f : 0.0f);
                            }
                            else {
                                o4 = 0.0;
                            }
                        }
                        else {
                            o4 = 0.0;
                        }
                        o3 = o4;
                    }
					o5 = this.speed.getValue();
                    if (mc.gameSettings.keyBindJump.isKeyDown() || mc.gameSettings.keyBindSneak.isKeyDown() || mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown()) {
                        if ((double)o5 != 0.0 || (double)o3 != 0.0 || (double)o5 != 0.0) {
                            if (mc.thePlayer.movementInput.jump && (mc.thePlayer.movementInput.moveStrafe != 0.0f || mc.thePlayer.movementInput.moveForward != 0.0f)) {
                                mc.thePlayer.setVelocity(0.0, 0.0, 0.0);
                                this.move(0.0, 0.0, 0.0);
                                o6 = false;
                                while ((int)o6 <= 3) {
                                    mc.thePlayer.setVelocity(0.0,(double) o3 * (double)o6, 0.0);
                                    this.move(0.0,(double) o3 * (double)o6, 0.0);
                                    o6 = (double)o6+1;
                                }
                            }
                            else if (mc.thePlayer.movementInput.jump) {
                                mc.thePlayer.setVelocity(0.0, 0.0, 0.0);
                                this.move(0.0, 0.0, 0.0);
                                o7 = false;
                                while ((int)o7 <= 3) {
                                    mc.thePlayer.setVelocity(0.0, (double)o3 * (double)o7, 0.0);
                                    this.move(0.0, (double)o3 * (double)o7, 0.0);
                                    o7 = (double)o7+1;
                                }
                            }
                            else {
                            	o8 = false;
                                while ((int)o8 <= 2) {
                                    mc.thePlayer.setVelocity((double)o5 * (double)o8, (double)o3 * (double)o8, (double)o5 * (double)o8);
                                    this.move((double)o5 * (double)o8, (double)o3 * (double)o8, (double)o5 * (double)o8);
                                    o8 = (double)o8+1;
                                }
                            }
                        }
                    }
                    else if (this.nokick.getValue() && mc.theWorld.getCollidingBoundingBoxes((Entity)mc.thePlayer, mc.thePlayer.boundingBox.expand(-0.0625, -0.0625, -0.0625)).isEmpty()) {
                        mc.thePlayer.setVelocity(0.0, (mc.thePlayer.ticksExisted % 2 == 0) ? 0.03999999910593033 : -0.03999999910593033, 0.0);
                        this.move(0.0, (mc.thePlayer.ticksExisted % 2 == 0) ? 0.03999999910593033 : -0.03999999910593033, 0.0);
                    }
				}
				return;
			}
		}
		if(mode.getValue() == "Vanilla")
		mc.thePlayer.capabilities.isFlying = true;
		super.onUpdate();
	}

	
	public void undrawSettings() {
		for(int i = 0; i < settings.size(); i++) {
			if(settings.get(i) != mode && settings.get(i) != shown)
			settings.get(i).setDraw(false);
		}
	}
	
	private void move(final double x, final double y, final double z) {
        Packet pos = new Packet11PlayerPosition(mc.thePlayer.posX + x, mc.thePlayer.posY + y, mc.thePlayer.boundingBox.minX - y, mc.thePlayer.posZ + z, mc.thePlayer.onGround);
        this.packets.add(pos);
        mc.thePlayer.sendQueue.addToSendQueue((Packet)pos);
        Packet bounds = new Packet11PlayerPosition(mc.thePlayer.posX + x, 0.0, mc.thePlayer.boundingBox.minY - mc.thePlayer.posY, mc.thePlayer.posZ + z, mc.thePlayer.onGround);
        this.packets.add(bounds);
        mc.thePlayer.sendQueue.addToSendQueue((Packet)bounds);
        ++this.teleportId;
    }
	
	public String lastString;
	public ModeSetting mode = new ModeSetting("Mode", "Which fly mode to use.", "Vanilla", "Xulu") {
		@Override
		public void onChange() {
			if(lastString != mode.getValue()) {
				lastString = mode.getValue();
				if(mode.getValue() == "Vanilla") {
					undrawSettings();
					mode.setDraw(true);
				}
				if(mode.getValue() == "WurstPlus") {
					undrawSettings();
					mode.setDraw(true);
					speed.setDraw(true);
					nokick.setDraw(true);
				}
				if(mode.getValue() == "Xulu") {
					undrawSettings();
					mode.setDraw(true);
					cooldown.setDraw(true);
					fallSpeed.setDraw(true);
					upSpeed.setDraw(true);
				}
			}
		}
	};
	
}
