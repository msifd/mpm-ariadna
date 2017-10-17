package noppes.mpm.client;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;

public class PresetController {
    public static PresetController instance;
    public HashMap<String, Preset> presets = new HashMap();
    private File dir;

    public PresetController(File dir) {
        instance = this;
        this.dir = dir;
    }

    public Preset getPreset(String username) {
        if (this.presets.isEmpty())
            load();
        return (Preset) this.presets.get(username.toLowerCase());
    }

    public void load() {
        NBTTagCompound compound = loadPreset();
        HashMap<String, Preset> presets = new HashMap();
        if (compound != null) {
            NBTTagList list = compound.getTagList("Presets", 10);
            for (int i = 0; i < list.tagCount(); i++) {
                NBTTagCompound comp = list.getCompoundTagAt(i);
                Preset preset = new Preset();
                preset.readFromNBT(comp);
                presets.put(preset.name.toLowerCase(), preset);
            }
        }
        Preset.FillDefault(presets);
        this.presets = presets;
    }

    private NBTTagCompound loadPreset() {
        String filename = "presets.dat";
        try {
            File file = new File(this.dir, filename);
            if (!file.exists()) {
                return null;
            }
            return CompressedStreamTools.readCompressed(new FileInputStream(file));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            try {
                File file = new File(this.dir, filename + "_old");
                if (!file.exists()) {
                    return null;
                }
                return CompressedStreamTools.readCompressed(new FileInputStream(file));
            } catch (Exception ee) {
                System.err.println(ee.getMessage());
            }
        }
        return null;
    }

    public void save() {
        NBTTagCompound compound = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        for (Preset preset : this.presets.values()) {
            list.appendTag(preset.writeToNBT());
        }

        compound.setTag("Presets", list);
        savePreset(compound);
    }

    private void savePreset(NBTTagCompound compound) {
        String filename = "presets.dat";
        try {
            File file = new File(this.dir, filename + "_new");
            File file1 = new File(this.dir, filename + "_old");
            File file2 = new File(this.dir, filename);
            CompressedStreamTools.writeCompressed(compound, new FileOutputStream(file));
            if (file1.exists()) {
                file1.delete();
            }
            file2.renameTo(file1);
            if (file2.exists()) {
                file2.delete();
            }
            file.renameTo(file2);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void addPreset(Preset preset) {
        while (this.presets.containsKey(preset.name.toLowerCase())) {
            preset.name += "_";
        }
        this.presets.put(preset.name.toLowerCase(), preset);
        save();
    }

    public void removePreset(String preset) {
        if (preset == null)
            return;
        this.presets.remove(preset.toLowerCase());
        save();
    }
}
