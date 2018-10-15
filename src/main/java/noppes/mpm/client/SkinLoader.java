package noppes.mpm.client;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.util.ResourceLocation;
import noppes.mpm.ModelData;
import noppes.mpm.client.net.ImageBufferDownloadAlt;

import java.io.File;
import java.util.Map;

public class SkinLoader {
    public static ResourceLocation loadSkin(AbstractClientPlayer player, ModelData data) {
        if (data.loaded)
            return data.playerResource;

        final Minecraft mc = Minecraft.getMinecraft();
        final SkinManager skinmanager = mc.func_152342_ad();
        final GameProfile gp = player.getGameProfile();
        final Map map = skinmanager.func_152788_a(gp);

        final String url;

        if ((data.url != null) && (!data.url.isEmpty())) {
            url = data.url;
        } else {
            final MinecraftProfileTexture profileSkin = (MinecraftProfileTexture) map.get(MinecraftProfileTexture.Type.SKIN);
            if (profileSkin == null) {
                data.loaded = true;
                data.playerResource = player.getLocationSkin();
                return data.playerResource;
            } else {
                url = profileSkin.getUrl();
            }
        }

        final File skinFile = getSkinFileForName(gp.getName());
        if (skinFile.exists())
            skinFile.delete();

        final ResourceLocation location = new ResourceLocation("skins/" + gp.getName());
        loadPlayerTexture(data, skinFile, location, url);
        player.func_152121_a(MinecraftProfileTexture.Type.SKIN, location);

        data.playerResource = location;
        data.loaded = true;
        return location;
    }

    private static File getSkinFileForName(String name) {
        final SkinManager skinmanager = Minecraft.getMinecraft().func_152342_ad();
        final String skinSubfolder = name.substring(0, 2);
        final File skinsDir = new File((File) ObfuscationReflectionHelper.getPrivateValue(SkinManager.class, skinmanager, 3), skinSubfolder);
        return new File(skinsDir, name);
    }

    private static void loadPlayerTexture(ModelData data, File file, ResourceLocation resource, String url) {
        final TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
        final ImageBufferDownloadAlt imageBuffer = new ImageBufferDownloadAlt(data);
        final ThreadDownloadImageData object = new ThreadDownloadImageData(file, url, SkinManager.field_152793_a, imageBuffer);
        texturemanager.loadTexture(resource, object);
    }
}
