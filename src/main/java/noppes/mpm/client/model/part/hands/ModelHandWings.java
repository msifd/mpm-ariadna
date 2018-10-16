package noppes.mpm.client.model.part.hands;

import noppes.mpm.client.model.Model2DRenderer;
import noppes.mpm.client.model.ModelMPM;
import noppes.mpm.client.model.ModelPartInterface;
import noppes.mpm.data.ModelData;
import noppes.mpm.data.ModelPartData;
import noppes.mpm.data.PartName;

public class ModelHandWings extends ModelPartInterface {
    private Model2DRenderer model;
    private boolean isRight;

    public ModelHandWings(ModelMPM base, boolean isRight) {
        super(base);
        this.isRight = isRight;

        model = new Model2DRenderer(base, 56.0F, 16.0F, 8, 16);
        model.setRotationPoint(isRight ? -1.5f : 0.5f, 0.0F, 2.0F);
        model.setRotationOffset(-16.0F, -12.0F);
        model.rotateAngleY = (float) (Math.PI / 2);

        addChild(model);
    }

    @Override
    public void initData(ModelData data) {
        final ModelPartData config = data.getPartData(PartName.HAND_WINGS);
        if (config == null) {
            this.isHidden = true;
            return;
        }

        this.color = config.color;
        this.isHidden = false;
        this.location = config.playerTexture ? null : config.getResource();
    }
}
