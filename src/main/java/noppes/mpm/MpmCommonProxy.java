package noppes.mpm;

import noppes.mpm.server.PacketHandlerServer;

public class MpmCommonProxy {
    public void load() {
        MorePlayerModels.CHANNEL.register(new PacketHandlerServer());
    }
}
