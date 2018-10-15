package noppes.mpm.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.mpm.data.ModelData;
import noppes.mpm.data.PlayerDataController;
import noppes.mpm.constants.EnumAnimation;
import noppes.mpm.constants.EnumPackets;
import noppes.mpm.server.Server;

public class CommandSit extends MpmCommandInterface {
    public String getCommandName() {
        return "sit";
    }

    public void processCommand(ICommandSender icommandsender, String[] var2) {
        if (!(icommandsender instanceof EntityPlayerMP))
            return;
        EntityPlayerMP player = (EntityPlayerMP) icommandsender;
        ModelData data = PlayerDataController.instance.getPlayerData(player);
        EnumAnimation ani = data.animation == EnumAnimation.SITTING ? EnumAnimation.NONE : EnumAnimation.SITTING;
        Server.sendAssociatedData(player, EnumPackets.ANIMATION, new Object[]{player.getCommandSenderName(), ani});
        data.animation = ani;
    }

    public String getCommandUsage(ICommandSender icommandsender) {
        return "/sit to sit down";
    }
}
