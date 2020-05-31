package noppes.mpm.core;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.DependsOn("forge")
@IFMLLoadingPlugin.SortingIndex(-100)
@IFMLLoadingPlugin.TransformerExclusions({"noppes.mpm.core"})
public class MpmCorePlugin implements IFMLLoadingPlugin {
    static boolean isDevEnv = false;

    @Override
    public String[] getASMTransformerClass() {
        return new String[] {
                CameraTransformer.class.getName()
        };
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        isDevEnv = !(boolean) data.get("runtimeDeobfuscationEnabled");
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
