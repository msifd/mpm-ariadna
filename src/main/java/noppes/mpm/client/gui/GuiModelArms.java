package noppes.mpm.client.gui;

import net.minecraft.client.gui.GuiScreen;
import noppes.mpm.client.gui.util.GuiModelInterface;
import noppes.mpm.client.gui.util.GuiNpcButton;
import noppes.mpm.client.gui.util.GuiNpcLabel;
import noppes.mpm.data.ModelPartData;
import noppes.mpm.data.PartName;

public class GuiModelArms extends GuiModelInterface {
    private final String[] arrParticles = {"gui.no", "Both", "Left", "Right"};
    private final String[] arrAmputee = {"gui.no", "Both", "Left", "Right"};
    private final String[] arrWings = {"gui.no", "Player", "Type1", "Type2", "Rainbow"};
    private GuiScreen parent;

    public GuiModelArms(GuiScreen parent) {
        this.parent = parent;
        this.xOffset = 60;
    }

    public void initGui() {
        super.initGui();
        int y = this.guiTop + 20;

        ModelPartData claws = this.playerdata.getPartData("claws");
        y += 22;
        addButton(new GuiNpcButton(0, this.guiLeft + 50, y, 70, 20, this.arrParticles, claws == null ? 0 : claws.type + 1));
        addLabel(new noppes.mpm.client.gui.util.GuiNpcLabel(0, "Claws", this.guiLeft, y + 5, 16777215));
        if (claws != null) {
            addButton(new GuiNpcButton(10, this.guiLeft + 122, y, 40, 20, claws.getColor()));
        }

        y += 22;
        byte amputee = this.playerdata.armsAmputee;
        addButton(new GuiNpcButton(1, this.guiLeft + 50, y, 70, 20, this.arrAmputee, amputee));
        addLabel(new GuiNpcLabel(1, "Amputee", this.guiLeft, y + 5, 16777215));

        y += 22;
        ModelPartData wings = this.playerdata.getPartData(PartName.HAND_WINGS);
        addButton(new GuiNpcButton(2, this.guiLeft + 50, y, 70, 20, this.arrWings, wings == null ? 0 : wings.type + 1));
        addLabel(new GuiNpcLabel(2, "Hand Wings", this.guiLeft, y + 5, 16777215));
        if (wings != null) {
            addButton(new GuiNpcButton(11, this.guiLeft + 122, y, 40, 20, wings.getColor()));
        }
    }

    protected void actionPerformed(net.minecraft.client.gui.GuiButton btn) {
        super.actionPerformed(btn);
        GuiNpcButton button = (GuiNpcButton) btn;

        if (button.id == 0) {
            if (button.getValue() == 0) {
                this.playerdata.removePart("claws");
            } else {
                ModelPartData data = this.playerdata.getOrCreatePart("claws");
                data.type = ((byte) (button.getValue() - 1));
            }
            initGui();
        }
        if (button.id == 1) {
            byte nextAmputee = this.playerdata.armsAmputee;
            nextAmputee++;
            if (nextAmputee > 3) nextAmputee = 0;
            this.playerdata.armsAmputee = nextAmputee;
            initGui();
        }
        if (button.id == 2) {
            if (button.getValue() == 0) {
                this.playerdata.removePart(PartName.HAND_WINGS);
            } else {
                final ModelPartData data = this.playerdata.getOrCreatePart(PartName.HAND_WINGS);
                final byte type = (byte) (button.getValue() - 1);
                if (type == 0)
                    data.setTexture("", type);
                else
                    data.setTexture("wings/wing" + type, type);
            }
            initGui();
        }

        if (button.id == 10) {
            this.mc.displayGuiScreen(new GuiModelColor(this, this.playerdata.getPartData("claws")));
        }
        if (button.id == 11) {
            this.mc.displayGuiScreen(new GuiModelColor(this, this.playerdata.getPartData(PartName.HAND_WINGS)));
        }
    }

    public void close() {
        this.mc.displayGuiScreen(this.parent);
    }
}
