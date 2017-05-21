package noppes.mpm.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;

public class VersionChecker extends Thread
{
  private int revision = 7;
  

  public void run()
  {
    String name = "§2MorePlayerModels§f";
    String link = "§9§nClick here";
    String text = name + " installed. More info at " + link;
    if (hasUpdate()) {
      text = name + "§4 update available " + link;
    }

    EntityPlayer player;
    try
    {
      player = Minecraft.getMinecraft().thePlayer;
    } catch (NoSuchMethodError e) {
      return;
    }
    while (player == null) {
      try {
        Thread.sleep(2000L);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    ChatComponentTranslation message = new ChatComponentTranslation(text, new Object[0]);
    message.getChatStyle().setChatClickEvent(new net.minecraft.event.ClickEvent(Action.OPEN_URL, "http://www.kodevelopment.nl/minecraft/moreplayermodels/"));
    player.addChatMessage(message);
  }
  
  private boolean hasUpdate() {
    try {
      URL url = new URL("https://dl.dropboxusercontent.com/u/3096920/update/minecraft/1.7/MorePlayerModels.txt");
      URLConnection yc = url.openConnection();
      BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
      String inputLine = in.readLine();
      if (inputLine == null)
        return false;
      int newVersion = Integer.parseInt(inputLine);
      return this.revision < newVersion;
    }
    catch (Exception e) {}
    
    return false;
  }
}
