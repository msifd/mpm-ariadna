package noppes.mpm.client.render;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.MPMRendererHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import noppes.mpm.ModelData;
import noppes.mpm.MorePlayerModels;
import noppes.mpm.PlayerDataController;
import noppes.mpm.constants.EnumAnimation;
import org.lwjgl.opengl.GL11;

public class RenderEventHandler {
    public static RenderMPM renderer = new RenderMPM();
    private ModelData data;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void pre(RenderPlayerEvent.Pre event) {
        EntityPlayer player = event.entityPlayer;
        
        data = PlayerDataController.instance.getPlayerData(player);
        renderer.setModelData(data, player);
        setModels(event.renderer);
        
        renderer.checkSkinState(player);

        if (!(event.renderer instanceof RenderMPM)) {
            RenderManager.instance.entityRenderMap.put(EntityPlayer.class, renderer);
            RenderManager.instance.entityRenderMap.put(EntityPlayerSP.class, renderer);
            RenderManager.instance.entityRenderMap.put(EntityPlayerMP.class, renderer);
            RenderManager.instance.entityRenderMap.put(EntityOtherPlayerMP.class, renderer);
            RenderManager.instance.entityRenderMap.put(EntityClientPlayerMP.class, renderer);
            RenderManager.instance.entityRenderMap.put(AbstractClientPlayer.class, renderer);
        }

        EntityLivingBase entity = data.getEntity(player.worldObj, player);
        renderer.setEntity(entity);
        if (player == Minecraft.getMinecraft().thePlayer) {
            player.yOffset = 1.62F;
            data.backItem = player.inventory.mainInventory[0];
        }
    }
    
    private void setModels(RenderPlayer render) {
        if (MPMRendererHelper.getMainModel(render) == renderer.modelBipedMain)
            return;
        ReflectionHelper.setPrivateValue(RenderPlayer.class, render, renderer.modelBipedMain, 1);
        ReflectionHelper.setPrivateValue(RenderPlayer.class, render, renderer.modelArmorChestplate, 2);
        ReflectionHelper.setPrivateValue(RenderPlayer.class, render, renderer.modelArmor, 3);
        MPMRendererHelper.setMainModel(render, renderer.modelBipedMain);
    }

    @SubscribeEvent
    public void special(RenderPlayerEvent.Specials.Pre event) {
        if (data.animation == EnumAnimation.BOW) {
            float ticks = (event.entityPlayer.ticksExisted - data.animationStart) / 10.0F;
            if (ticks > 1.0F)
                ticks = 1.0F;
            float scale = 2.0F - data.body.scaleY;
            GL11.glTranslatef(0.0F, 12.0F * scale * 0.065F, 0.0F);
            GL11.glRotatef(60.0F * ticks, 1.0F, 0.0F, 0.0F);
            GL11.glTranslatef(0.0F, -12.0F * scale * 0.065F, 0.0F);
        }
        event.renderItem = false;
        event.renderHelmet = false;
        renderer.renderItem(event.entityPlayer);
        renderer.renderHelmet(event.entityPlayer);
        if (MorePlayerModels.EnableBackItem)
            renderer.renderBackitem(event.entityPlayer);
        GL11.glTranslatef(0.0F, data.getBodyY(), 0.0F);
    }
}
