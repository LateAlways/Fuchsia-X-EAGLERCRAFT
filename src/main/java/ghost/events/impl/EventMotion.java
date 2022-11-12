package ghost.events.impl;

import ghost.events.Event;

public class EventMotion extends Event {

	public float getLastYaw() {
		return lastYaw;
	}
	public EventMotion setLastYaw(float lastYaw) {
		this.lastYaw = lastYaw;
		return this;
	}
	public float getLastPitch() {
		return lastPitch;
	}
	public EventMotion setLastPitch(float lastPitch) {
		this.lastPitch = lastPitch;
		return this;
	}
	public double posX;
	public double posY;
	public double posZ;
	public double minY;
	public float yaw;
	public float pitch;
	
	public float lastYaw;
	public float lastPitch;
	
	public double motionX;
	public double motionY;
	public double motionZ;
	
	public boolean onGround;
	
	public double getMotionX() {
		return motionX;
	}
	public void setMotionX(double motionX) {
		this.motionX = motionX;
	}
	public double getMotionY() {
		return motionY;
	}
	public void setMotionY(double motionY) {
		this.motionY = motionY;
	}
	public double getMotionZ() {
		return motionZ;
	}
	public void setMotionZ(double motionZ) {
		this.motionZ = motionZ;
	}
	public boolean isOnGround() {
		return onGround;
	}
	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}
	public double getPosX() {
		return posX;
	}
	public void setPosX(double posX) {
		this.posX = posX;
	}
	public double getPosY() {
		return posY;
	}
	public void setPosY(double posY) {
		this.posY = posY;
	}
	public double getPosZ() {
		return posZ;
	}
	public void setPosZ(double posZ) {
		this.posZ = posZ;
	}
	public double getMinY() {
		return minY;
	}
	public void setMinY(double minY) {
		this.minY = minY;
	}
	public float getYaw() {
		return yaw;
	}
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	public float getPitch() {
		return pitch;
	}
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
}
