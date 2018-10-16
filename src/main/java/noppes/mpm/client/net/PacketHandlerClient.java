package noppes.mpm.client.net;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import noppes.mpm.constants.EnumPackets;
import noppes.mpm.data.ModelData;
import noppes.mpm.data.PlayerDataController;
import noppes.mpm.server.PacketHandlerServer;
import noppes.mpm.server.Server;
import noppes.mpm.utils.LogWriter;

import java.io.IOException;


public class PacketHandlerClient extends PacketHandlerServer {
    @SubscribeEvent
    public void onPacketData(ClientCustomPacketEvent event) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        ByteBuf buf = event.packet.payload();
        try {
            handlePacket(buf, player, EnumPackets.values()[buf.readInt()]);
        } catch (Exception e) {
            LogWriter.except(e);
        }
    }

    private void handlePacket(ByteBuf buffer, EntityPlayer player, EnumPackets type) throws IOException {
        if (type == EnumPackets.PING) {
            noppes.mpm.MorePlayerModels.HasServerSide = true;
        } else if (type == EnumPackets.SEND_PLAYER_DATA) {
            EntityPlayer pl = player.worldObj.getPlayerEntityByName(Server.readString(buffer));
            if (pl == null)
                return;
            ModelData data = PlayerDataController.instance.getPlayerData(pl);
            NBTTagCompound compound = Server.readNBT(buffer);
            data.readFromNBT(compound);
            PlayerDataController.instance.savePlayerData(pl, data);
        } else if (type == EnumPackets.BACK_ITEM_REMOVE) {
            EntityPlayer pl = player.worldObj.getPlayerEntityByName(Server.readString(buffer));
            if (pl == null)
                return;
            ModelData data = PlayerDataController.instance.getPlayerData(pl);
            data.backItem = null;
        } else if (type == EnumPackets.BACK_ITEM_UPDATE) {
            EntityPlayer pl = player.worldObj.getPlayerEntityByName(Server.readString(buffer));
            if (pl == null)
                return;
            NBTTagCompound compound = Server.readNBT(buffer);
            ItemStack item = ItemStack.loadItemStackFromNBT(compound);
            ModelData data = PlayerDataController.instance.getPlayerData(pl);
            data.backItem = item;
        } else if (type == EnumPackets.PARTICLE) {
            int animation = buffer.readInt();
            if (animation == 0) {
                EntityPlayer pl = player.worldObj.getPlayerEntityByName(Server.readString(buffer));
                if (pl == null)
                    return;
                ModelData data = PlayerDataController.instance.getPlayerData(pl);
                data.inLove = 40;
            } else if (animation == 1) {
                player.worldObj.spawnParticle("note", buffer.readDouble(), buffer.readDouble(), buffer.readDouble(), buffer.readDouble(), 0.0D, 0.0D);
            } else if (animation == 2) {
                EntityPlayer pl = player.worldObj.getPlayerEntityByName(Server.readString(buffer));
                if (pl == null)
                    return;
                ModelData data = PlayerDataController.instance.getPlayerData(pl);
                for (int i = 0; i < 5; i++) {
                    double d0 = player.getRNG().nextGaussian() * 0.02D;
                    double d1 = player.getRNG().nextGaussian() * 0.02D;
                    double d2 = player.getRNG().nextGaussian() * 0.02D;
                    double x = player.posX + (player.getRNG().nextFloat() - 0.5F) * player.width * 2.0F;
                    double z = player.posZ + (player.getRNG().nextFloat() - 0.5F) * player.width * 2.0F;
                    player.worldObj.spawnParticle("angryVillager", x, player.posY + 0.800000011920929D + player.getRNG().nextFloat() * player.height / 2.0F - player.getYOffset() - data.getBodyY(), z, d0, d1, d2);
                }
            }
        } else if (type == EnumPackets.ANIMATION) {
            EntityPlayer pl = player.worldObj.getPlayerEntityByName(Server.readString(buffer));
            if (pl == null)
                return;
            ModelData data = PlayerDataController.instance.getPlayerData(pl);
            data.setAnimation(buffer.readInt());
            data.animationStart = pl.ticksExisted;
        }
    }
}
