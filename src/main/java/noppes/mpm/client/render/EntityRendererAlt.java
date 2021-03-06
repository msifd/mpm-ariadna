package noppes.mpm.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import noppes.mpm.constants.EnumAnimation;
import noppes.mpm.data.ModelData;
import noppes.mpm.data.PlayerDataController;

public class EntityRendererAlt extends EntityRenderer {
    public EntityRendererAlt(Minecraft par1Minecraft) {
        super(par1Minecraft, par1Minecraft.getResourceManager());
    }

    private boolean isBlocked(EntityPlayer player) {
        int x = MathHelper.floor_double(player.posX);
        int y = MathHelper.floor_double(player.posY) + 1;
        int z = MathHelper.floor_double(player.posZ);
        return !player.worldObj.isAirBlock(x, y, z);
    }

    public void getMouseOver(float par1) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if ((player == null) || (player.isPlayerSleeping())) {
            super.getMouseOver(par1);
            return;
        }
        ModelData data = PlayerDataController.instance.getPlayerData(player);

        float offset = -data.offsetY();
        if (data.animation == EnumAnimation.SITTING) {
            offset += 0.5F - data.getLegsY();
        }
        if (data.isSleeping())
            offset = 1.18F;
        if ((offset < -0.2F) && (isBlocked(player)))
            offset = -0.2F;
        player.posY += -offset;
        player.prevPosY += -offset;
        player.lastTickPosY += -offset;
        super.getMouseOver(par1);
        player.posY -= -offset;
        player.prevPosY -= -offset;
        player.lastTickPosY -= -offset;
    }
}
