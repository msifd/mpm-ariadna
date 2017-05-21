package noppes.mpm;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import noppes.mpm.constants.EnumAnimation;
import noppes.mpm.constants.EnumPackets;

public class PacketHandlerServer
{
  @SubscribeEvent
  public void onPacketData(ServerCustomPacketEvent event)
  {
    EntityPlayerMP player = ((NetHandlerPlayServer)event.handler).playerEntity;
    ByteBuf buf = event.packet.payload();
    try {
      handlePacket(buf, player, EnumPackets.values()[buf.readInt()]);
    }
    catch (Exception e) {
      System.err.println(player + e.getMessage());
    }
  }
  
  private void handlePacket(ByteBuf buffer, EntityPlayerMP player, EnumPackets type) throws java.io.IOException {
    if (type == EnumPackets.PING) {
      ModelData data = PlayerDataController.instance.getPlayerData(player);
      data.readFromNBT(Server.readNBT(buffer));
      
      if (!player.worldObj.getGameRules().getGameRuleBooleanValue("mpmAllowEntityModels")) {
        data.entityClass = null;
      }
      
      Server.sendAssociatedData(player, EnumPackets.SEND_PLAYER_DATA, new Object[] { player.getCommandSenderName(), data.writeToNBT() });
      
      ItemStack back = player.inventory.mainInventory[0];
      if (back != null)
        Server.sendAssociatedData(player, EnumPackets.BACK_ITEM_UPDATE, new Object[] { player.getCommandSenderName(), back.writeToNBT(new NBTTagCompound()) });
      Server.sendData(player, EnumPackets.PING, new Object[0]);
    }
    else if (type == EnumPackets.REQUEST_PLAYER_DATA) {
      EntityPlayer pl = player.worldObj.getPlayerEntityByName(Server.readString(buffer));
      if (pl == null)
        return;
      String hash = Server.readString(buffer);
      ModelData data = PlayerDataController.instance.getPlayerData(pl);
      if (!hash.equals(data.getHash())) {
        Server.sendData(player, EnumPackets.SEND_PLAYER_DATA, new Object[] { pl.getCommandSenderName(), data.writeToNBT() });
      }
      ItemStack back = pl.inventory.mainInventory[0];
      if (back != null) {
        Server.sendData(player, EnumPackets.BACK_ITEM_UPDATE, new Object[] { pl.getCommandSenderName(), back.writeToNBT(new NBTTagCompound()) });
      } else {
        Server.sendData(player, EnumPackets.BACK_ITEM_REMOVE, new Object[] { pl.getCommandSenderName() });
      }
    } else if (type == EnumPackets.UPDATE_PLAYER_DATA) {
      ModelData data = PlayerDataController.instance.getPlayerData(player);
      data.readFromNBT(Server.readNBT(buffer));
      
      if (!player.worldObj.getGameRules().getGameRuleBooleanValue("mpmAllowEntityModels")) {
        data.entityClass = null;
      }
      PlayerDataController.instance.savePlayerData(player, data);
      Server.sendAssociatedData(player, EnumPackets.SEND_PLAYER_DATA, new Object[] { player.getCommandSenderName(), data.writeToNBT() });
    }
    else if (type == EnumPackets.ANIMATION)
    {
      EnumAnimation animation = EnumAnimation.values()[buffer.readInt()];
      if (animation == EnumAnimation.SLEEPING_SOUTH) {
        float rotation = player.rotationYaw;
        while (rotation < 0.0F)
          rotation += 360.0F;
        while (rotation > 360.0F)
          rotation -= 360.0F;
        int rotate = (int)((rotation + 45.0F) / 90.0F);
        if (rotate == 1)
          animation = EnumAnimation.SLEEPING_WEST;
        if (rotate == 2)
          animation = EnumAnimation.SLEEPING_NORTH;
        if (rotate == 3)
          animation = EnumAnimation.SLEEPING_EAST;
      }
      ModelData data = PlayerDataController.instance.getPlayerData(player);
      if (data.animationEquals(animation)) {
        animation = EnumAnimation.NONE;
      }
      Server.sendAssociatedData(player, EnumPackets.ANIMATION, new Object[] { player.getCommandSenderName(), animation });
      data.animation = animation;
    }
  }
}
