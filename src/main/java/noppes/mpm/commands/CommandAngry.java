package noppes.mpm.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.mpm.constants.EnumPackets;
import noppes.mpm.server.Server;

public class CommandAngry extends MpmCommandInterface {
    public String getCommandName() {
        return "angry";
    }

    public void processCommand(ICommandSender icommandsender, String[] var2) {
        if (!(icommandsender instanceof EntityPlayerMP))
            return;
        EntityPlayerMP player = (EntityPlayerMP) icommandsender;
        Server.sendAssociatedData(player, EnumPackets.PARTICLE, new Object[]{Integer.valueOf(2), player.getCommandSenderName()});
    }

    public String getCommandUsage(ICommandSender icommandsender) {
        return "/angry to show your angry";
    }
}
