package noppes.mpm;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;
import net.minecraftforge.common.MinecraftForge;
import noppes.mpm.commands.*;
import noppes.mpm.config.ConfigLoader;
import noppes.mpm.config.ConfigProp;
import noppes.mpm.constants.EnumAnimation;
import noppes.mpm.data.PlayerDataController;
import noppes.mpm.server.ServerEventHandler;
import noppes.mpm.server.ServerTickHandler;

import java.io.File;


@Mod(modid = "moreplayermodels", name = "MorePlayerModels", version = "@VERSION@")
public class MorePlayerModels {
    @ConfigProp
    public static int Tooltips = 2;

    @SidedProxy(clientSide = "noppes.mpm.client.MpmClientProxy", serverSide = "noppes.mpm.MpmCommonProxy")
    public static MpmCommonProxy PROXY;

    @Mod.Instance
    public static MorePlayerModels INSTANCE;

    public static FMLEventChannel CHANNEL;

    public static int Revision = 5;
    public static boolean HasServerSide = false;
    @ConfigProp(info = "Enable different perspective heights for different model sizes")
    public static boolean EnablePOV = true;
    @ConfigProp(info = "Enables the item on your back")
    public static boolean EnableBackItem = true;
    @ConfigProp(info = "Enables chat bubbles")
    public static boolean EnableChatBubbles = true;
    @ConfigProp(info = "Enables MorePlayerModels startup update message")
    public static boolean EnableUpdateChecker = true;
    @ConfigProp(info = "Used to register buttons to legsAnimationHandler")
    public static int button1 = EnumAnimation.SLEEPING_SOUTH.ordinal();
    @ConfigProp(info = "Used to register buttons to legsAnimationHandler")
    public static int button2 = EnumAnimation.SITTING.ordinal();
    @ConfigProp(info = "Used to register buttons to legsAnimationHandler")
    public static int button3 = EnumAnimation.CRAWLING.ordinal();
    @ConfigProp(info = "Used to register buttons to legsAnimationHandler")
    public static int button4 = EnumAnimation.HUG.ordinal();
    @ConfigProp(info = "Used to register buttons to legsAnimationHandler")
    public static int button5 = EnumAnimation.DANCING.ordinal();

    public File dir;
    public ConfigLoader configLoader;

    private final ServerEventHandler serverEventHandler = new ServerEventHandler();

    @Mod.EventHandler
    public void load(FMLPreInitializationEvent ev) {
        CHANNEL = NetworkRegistry.INSTANCE.newEventDrivenChannel("MorePlayerModels");

        MinecraftServer server = MinecraftServer.getServer();
        String dir = "";
        if (server != null) {
            dir = new File(".").getAbsolutePath();
        } else {
            dir = Minecraft.getMinecraft().mcDataDir.getAbsolutePath();
        }
        this.dir = new File(dir, "moreplayermodels");
        if (!this.dir.exists()) {
            this.dir.mkdir();
        }
        new PlayerDataController(this.dir);

        this.configLoader = new ConfigLoader(getClass(), new File(dir, "config"), "MorePlayerModels");
        this.configLoader.loadConfig();

        if (Loader.isModLoaded("Morph")) {
            EnablePOV = false;
        }
        PROXY.load();

        serverEventHandler.init();
        FMLCommonHandler.instance().bus().register(new ServerTickHandler());
    }

    @Mod.EventHandler
    public void serverstart(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandLove());
        event.registerServerCommand(new CommandSing());
        event.registerServerCommand(new CommandSleep());
        event.registerServerCommand(new CommandHug());
        event.registerServerCommand(new CommandCrawl());
        event.registerServerCommand(new CommandSit());
        event.registerServerCommand(new CommandDance());
        event.registerServerCommand(new CommandWave());
        event.registerServerCommand(new CommandWag());
        event.registerServerCommand(new CommandBow());
        event.registerServerCommand(new CommandCry());
        event.registerServerCommand(new CommandAngry());
        event.registerServerCommand(new CommandSetUrl());

        GameRules rules = event.getServer().worldServerForDimension(0).getGameRules();
        if (!rules.hasRule("mpmAllowEntityModels")) {
            rules.addGameRule("mpmAllowEntityModels", "true");
        }
    }
}
