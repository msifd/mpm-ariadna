package noppes.mpm;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import noppes.mpm.server.PacketHandlerServer;

public class MpmCommonProxy {
    public void load() {
        MorePlayerModels.CHANNEL.register(new PacketHandlerServer());
    }
}
