package noppes.mpm.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import noppes.mpm.client.gui.util.GuiModelInterface;
import noppes.mpm.client.gui.util.GuiNpcButton;
import noppes.mpm.client.gui.util.GuiNpcLabel;
import noppes.mpm.client.gui.util.GuiNpcSlider;
import noppes.mpm.data.ModelData;
import noppes.mpm.data.ModelPartConfig;

public class GuiModelScale extends GuiModelInterface implements noppes.mpm.client.gui.util.ISliderListener {
    private GuiScreen parent;
    private int type = 0;
    private ModelData data;

    public GuiModelScale(GuiScreen parent, ModelData data) {
        this.parent = parent;
        this.data = data;
        this.xOffset = 100;
        this.ySize = 230;
    }

    public void initGui() {
        super.initGui();

        int y = this.guiTop + 2;

        addLabel(new GuiNpcLabel(20, "Head", this.guiLeft + 55, y + 5, 16777215));
        if (this.type == 0) {
            drawSlider(y, this.data.head);
            y += 88;
        } else {
            addButton(new GuiNpcButton(0, this.guiLeft + 110, y, 60, 20, "Edit"));
            y += 24;
        }

        addLabel(new GuiNpcLabel(21, "Body", this.guiLeft + 55, y + 5, 16777215));
        if (this.type == 1) {
            drawSlider(y, this.data.body);
            y += 88;
        } else {
            addButton(new GuiNpcButton(1, this.guiLeft + 110, y, 60, 20, "Edit"));
            y += 24;
        }

        addLabel(new GuiNpcLabel(22, "Arms", this.guiLeft + 55, y + 5, 16777215));
        if (this.type == 2) {
            drawSlider(y, this.data.arms);
            y += 88;
        } else {
            addButton(new GuiNpcButton(2, this.guiLeft + 110, y, 60, 20, "Edit"));
            y += 24;
        }

        addLabel(new GuiNpcLabel(23, "Legs", this.guiLeft + 55, y + 5, 16777215));
        if (this.type == 3) {
            drawSlider(y, this.data.legs);
            y += 88;
        } else {
            addButton(new GuiNpcButton(3, this.guiLeft + 110, y, 60, 20, "Edit"));
            y += 24;
        }
    }

    private void drawSlider(int y, ModelPartConfig config) {
        y += 15;
        addLabel(new GuiNpcLabel(10, "Width", this.guiLeft, y + 5, 16777215));
        addSlider(new GuiNpcSlider(this, 10, this.guiLeft + 50, y, (config.scaleX - 0.25F) / 2.0F));
        y += 22;
        addLabel(new GuiNpcLabel(11, "Height", this.guiLeft, y + 5, 16777215));
        addSlider(new GuiNpcSlider(this, 11, this.guiLeft + 50, y, (config.scaleY - 0.25F) / 2.0F));
        y += 22;
        addLabel(new GuiNpcLabel(12, "Depth", this.guiLeft, y + 5, 16777215));
        addSlider(new GuiNpcSlider(this, 12, this.guiLeft + 50, y, (config.scaleZ - 0.25F) / 2.0F));
    }


    protected void actionPerformed(GuiButton btn) {
        super.actionPerformed(btn);
        if (btn.id < 4) {
            this.type = btn.id;
            initGui();
        }
    }

    public void close() {
        this.mc.displayGuiScreen(this.parent);
    }

    public void mouseDragged(GuiNpcSlider slider) {
        float value = 0.25F + slider.sliderValue * 2.0F;
        int percent = (int) (value * 100);
        slider.setString(percent + "%");
        ModelPartConfig config = this.data.head;
        if (this.type == 1) {
            config = this.data.body;
        } else if (this.type == 2) {
            config = this.data.arms;
        } else if (this.type == 3) {
            config = this.data.legs;
        }
        if (slider.id == 10)
            config.scaleX = value;
        if (slider.id == 11)
            config.scaleY = value;
        if (slider.id == 12) {
            config.scaleZ = value;
        }
    }

    public void mousePressed(GuiNpcSlider slider) {
    }

    public void mouseReleased(GuiNpcSlider slider) {
    }
}
