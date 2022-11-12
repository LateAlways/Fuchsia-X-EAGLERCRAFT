package ghost.mods.impl.combat;

import java.util.List;

import ghost.mods.essential.Category;
import ghost.mods.essential.Mod;
import ghost.mods.essential.settings.BooleanSetting;
import ghost.mods.essential.settings.NumberSetting;
import ghost.utils.RotationUtils;
import ghost.utils.Timer;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EnumMovingObjectType;
import net.minecraft.src.Vec3;

public class AimAssist extends Mod {

	public NumberSetting hit = new NumberSetting("Hitbox Size","Self-Explanatory.", 3, 1, 5, 0.5F,0.5F);
	
	public AimAssist() {
		super("AimAssist", "Help you aim towards your targets.", Category.COMBAT);
		addSetting(hit);
	}
	
	public float sensitivity = 0;
	public boolean aiming = false;
	
	public EntityLiving getClosestEntity() {
		List<Entity> entitylist = mc.theWorld.getLoadedEntityList();
		EntityLiving closestEntity = null;
		float closest = -1;
		for(Entity e : entitylist) {
			if(closest == -1 && (e instanceof EntityLiving) && e != mc.thePlayer) {
				closest = mc.thePlayer.getDistanceToEntity(e);
				closestEntity = (EntityLiving) e;
				continue;
			}
			if(mc.thePlayer.getDistanceToEntity(e) < closest && (e instanceof EntityLiving) && e != mc.thePlayer) {
				closest = mc.thePlayer.getDistanceToEntity(e);
				closestEntity = (EntityLiving) e;
			}
		}
		return closestEntity;
	}
	
	public float[] getRotations(Entity e) {
        double deltaX = e.posX + (e.posX - e.lastTickPosX) - mc.thePlayer.posX,
                deltaY = e.posY - 3.5 + e.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),
                deltaZ = e.posZ + (e.posZ - e.lastTickPosZ) - mc.thePlayer.posZ,
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
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		try {
			if(mc.objectMouseOver != null) {
				if(mc.objectMouseOver.typeOfHit == EnumMovingObjectType.ENTITY) {
					if(mc.objectMouseOver.entityHit != null && aiming == false) {
						aiming = true;
						//mc.gameSettings.mouseSensitivity = (float) (mc.gameSettings.mouseSensitivity/1.5);
					}
				}
			} else {
				aiming = false;
				//mc.gameSettings.mouseSensitivity = sensitivity;
			}
		} catch(Exception e) {
			//System.out.println(e);
		}
	}
}
