package ghost.utils;

import net.lax1dude.eaglercraft.EaglercraftRandom;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Vec3;

public class RotationUtils {
	
	public static float[] getRotations(Entity e) {
		double deltaX = e.posX + (e.posX - e.lastTickPosX) - Minecraft.getMinecraft().thePlayer.posX,
			   deltaY = e.posY - 0.7+ e.getEyeHeight() - Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight(),
			   deltaZ = e.posZ + (e.posZ - e.lastTickPosZ) - Minecraft.getMinecraft().thePlayer.posZ,
			   distance = Math.sqrt(Math.pow(deltaX, 2)+Math.pow(deltaZ, 2));
		
		float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ)),
			  pitch = (float) -Math.toDegrees(Math.atan(deltaY / distance));
		
		if(deltaX < 0 && deltaZ < 0) {
			yaw = (float) (90 + Math.toDegrees(Math.atan(deltaZ/deltaX)));
		} else if(deltaX > 0 && deltaZ < 0) {
			yaw = (float) (-90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
		}
		
		return new float[] {yaw,pitch};
	}
	
	public static float[] getRotations(double x, double y, double z) {
		double deltaX = x - Minecraft.getMinecraft().thePlayer.posX,
			   deltaY = y - 0.7 - Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight(),
			   deltaZ = z - Minecraft.getMinecraft().thePlayer.posZ,
			   distance = Math.sqrt(Math.pow(deltaX, 2)+Math.pow(deltaZ, 2));
		
		float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ)),
			  pitch = (float) -Math.toDegrees(Math.atan(deltaY / distance));
		
		if(deltaX < 0 && deltaZ < 0) {
			yaw = (float) (90 + Math.toDegrees(Math.atan(deltaZ/deltaX)));
		} else if(deltaX > 0 && deltaZ < 0) {
			yaw = (float) (-90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
		}
		
		return new float[] {yaw,pitch};
	}
	
	public static void smoothFacePos(Vec3 vec, float aimspeed) {
		EaglercraftRandom rand = new EaglercraftRandom();
		float[] dest = getRotations(vec.xCoord,vec.yCoord,vec.zCoord);
		float yaw = dest[0];

		float pitch = dest[1];

		boolean aim = false;
		float max = 5;
		float yawChange = 0;
		if ((MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw)) > max * 2) {
			aim = true;
			yawChange = max;
		} else if ((MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw)) < -max * 2) {
			aim = true;
			yawChange = -max;
		}
		float pitchChange = 0;
		if ((MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch)) > max * 4) {
			aim = true;
			pitchChange = max;
		} else if ((MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch)) < -max
				* 4) {
			aim = true;
			pitchChange = -max;
		}
		// Minecraft.getMinecraft().thePlayer.rotationYaw += yawChange;
		// Minecraft.getMinecraft().thePlayer.rotationPitch += pitchChange;
		if (aim) {
			Minecraft.getMinecraft().thePlayer.rotationYaw += (MathHelper
					.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw))
					/ (aimspeed * (rand.nextDouble() * 2 + 1));
			Minecraft.getMinecraft().thePlayer.rotationPitch += (MathHelper
					.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch))
					/ (aimspeed * (rand.nextDouble() * 2 + 1));
		}

	}

	public static void smoothFacePos(Vec3 vec, double addSmoothing,float aimspeed) {
		double diffX = vec.xCoord + 0.5 - Minecraft.getMinecraft().thePlayer.posX;
		double diffY = vec.yCoord + 0.5
				- (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
		double diffZ = vec.zCoord + 0.5 - Minecraft.getMinecraft().thePlayer.posZ;
		double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
		float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;

		float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);

		Minecraft.getMinecraft().thePlayer.rotationYaw += (MathHelper
				.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw))
				/ (aimspeed * addSmoothing);
		Minecraft.getMinecraft().thePlayer.rotationPitch += (MathHelper
				.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch))
				/ (aimspeed * addSmoothing);
	}

}
