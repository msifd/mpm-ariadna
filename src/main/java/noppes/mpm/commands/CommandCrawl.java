package noppes.mpm.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.mpm.ModelData;
import noppes.mpm.PlayerDataController;
import noppes.mpm.server.Server;
import noppes.mpm.constants.EnumAnimation;
import noppes.mpm.constants.EnumPackets;

public class CommandCrawl extends MpmCommandInterface {
    public String getCommandName() {
        return "crawl";
    }

    public void processCommand(ICommandSender icommandsender, String[] var2) {
        if (!(icommandsender instanceof EntityPlayerMP))
            return;
        EntityPlayerMP player = (EntityPlayerMP) icommandsender;
        ModelData data = PlayerDataController.instance.getPlayerData(player);
        EnumAnimation ani = data.animation == EnumAnimation.CRAWLING ? EnumAnimation.NONE : EnumAnimation.CRAWLING;
        Server.sendAssociatedData(player, EnumPackets.ANIMATION, new Object[]{player.getCommandSenderName(), ani});
        data.animation = ani;
    }

    public String getCommandUsage(ICommandSender icommandsender) {
        return "/crawl to crawl";
    }
}
