package noppes.mpm.client.model.part;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import noppes.mpm.client.model.ModelMPM;
import noppes.mpm.client.model.ModelPartInterface;
import noppes.mpm.client.model.part.horns.ModelAntennasBack;
import noppes.mpm.client.model.part.horns.ModelAntennasFront;
import noppes.mpm.client.model.part.horns.ModelAntlerHorns;
import noppes.mpm.client.model.part.horns.ModelBullHorns;
import noppes.mpm.data.ModelData;
import noppes.mpm.data.ModelPartData;

public class ModelHorns extends ModelPartInterface {
    private ModelRenderer bull;
    private ModelRenderer antlers;
    private ModelRenderer antennasBack;
    private ModelRenderer antennasFront;
    private ModelRenderer foreheadAntlers;

    public ModelHorns(ModelMPM base) {
        super(base);

        addChild(this.bull = new ModelBullHorns(base));
        addChild(this.antlers = new ModelAntlerHorns(base));
        addChild(this.antennasBack = new ModelAntennasBack(base));
        addChild(this.antennasFront = new ModelAntennasFront(base));

        this.foreheadAntlers = new ModelRenderer(this.base);
        addChild(this.foreheadAntlers);
    }


    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {
    }


    public void initData(ModelData data) {
        ModelPartData config = data.getPartData("horns");
        if (config == null) {
            this.isHidden = true;
            return;
        }
        this.color = config.color;
        this.isHidden = false;

        if (this.foreheadAntlers.childModels != null)
            this.foreheadAntlers.childModels.clear();
        for (int i = 0; i < 2; i++) {
            final boolean leftSide = i % 2 == 0;
            final int side = leftSide ? -1 : 1;

            final ModelRenderer horn1 = new ModelRenderer(this.base, 32, 0);
            horn1.mirror = leftSide;
            horn1.addBox(-0.5f, 0, 0f, 1, 4, 1);
            horn1.setRotationPoint(-2 * side, -11f, -5f);

//            horn1.rotateAngleX = (float) Math.PI * 0.5f;
//            horn1.rotateAngleY = (float) Math.PI * -0.1f * side;
//            horn1.rotateAngleZ = (float) Math.PI * 0.5f * side;
            this.foreheadAntlers.addChild(horn1);
        }

        this.bull.isHidden = (config.type != 0);
        this.antlers.isHidden = (config.type != 1);
        this.antennasBack.isHidden = (config.type != 2);
        this.antennasFront.isHidden = (config.type != 3);
        this.foreheadAntlers.isHidden = (config.type != 4);

        if (!config.playerTexture) {
            this.location = config.getResource();
        } else {
            this.location = null;
        }
    }
}
