package noppes.mpm.client.model.part;

import net.minecraft.client.model.ModelRenderer;
import noppes.mpm.client.model.Model2DRenderer;
import noppes.mpm.data.ModelData;
import noppes.mpm.data.ModelPartData;

import java.util.List;

public class ModelEars extends noppes.mpm.client.model.ModelPartInterface {
    private ModelRenderer ears;
    private ModelRenderer bunny;
    private ModelRenderer shortElf;
    private ModelRenderer longElf;

    public ModelEars(noppes.mpm.client.model.ModelMPM par1ModelBase) {
        super(par1ModelBase);

        this.ears = new ModelRenderer(this.base);
        addChild(this.ears);

        Model2DRenderer right = new Model2DRenderer(this.base, 24.0F, 0.0F, 8, 4, 64.0F, 64.0F);
        right.setRotationPoint(-7.44F, -7.3F, -0.0F);
        right.setScale(0.234F, 0.234F);
        right.setThickness(1.16F);
        this.ears.addChild(right);

        Model2DRenderer left = new Model2DRenderer(this.base, 24.0F, 0.0F, 8, 4, 64.0F, 64.0F);
        left.setRotationPoint(7.44F, -7.3F, 1.15F);
        left.setScale(0.234F, 0.234F);
        setRotation(left, 0.0F, 3.1415927F, 0.0F);
        left.setThickness(1.16F);
        this.ears.addChild(left);

        Model2DRenderer right2 = new Model2DRenderer(this.base, 24.0F, 4.0F, 8, 4, 64.0F, 64.0F);
        right2.setRotationPoint(-7.44F, -7.3F, 1.14F);
        right2.setScale(0.234F, 0.234F);
        right2.setThickness(1.16F);
        this.ears.addChild(right2);

        Model2DRenderer left2 = new Model2DRenderer(this.base, 24.0F, 4.0F, 8, 4, 64.0F, 64.0F);
        left2.setRotationPoint(7.44F, -7.3F, 2.31F);
        left2.setScale(0.234F, 0.234F);
        setRotation(left2, 0.0F, 3.1415927F, 0.0F);
        left2.setThickness(1.16F);
        this.ears.addChild(left2);


        this.bunny = new ModelRenderer(this.base);
        addChild(this.bunny);

        ModelRenderer earleft = new ModelRenderer(this.base, 24, 0);
        earleft.mirror = true;
        earleft.addBox(-1.5F, -4.0F, 0.0F, 3, 7, 1);
        earleft.setRotationPoint(2.533333F, -11.0F, 0.0F);
        this.bunny.addChild(earleft);

        ModelRenderer earright = new ModelRenderer(this.base, 24, 0);
        earright.addBox(-1.5F, -4.0F, 0.0F, 3, 7, 1);
        earright.setRotationPoint(-2.466667F, -11.0F, 0.0F);
        this.bunny.addChild(earright);


        this.shortElf = new ModelRenderer(this.base);
        addChild(this.shortElf);
        for (int i = 0; i < 2; i++) {
            final boolean leftSide = i % 2 == 0;
            final int side = leftSide ? -1 : 1;
            final ModelRenderer ear = new ModelRenderer(this.base, 24, 0);
            ear.mirror = leftSide;
            ear.addBox(-1.5f, 0, -0.2f, 3, 5, 1);
            ear.setRotationPoint(-4.2f * side, -4f, -1.5f);
            ear.rotateAngleX = (float) Math.PI * 0.4f;
            ear.rotateAngleY = (float) Math.PI * -0.1f * side;
            ear.rotateAngleZ = (float) Math.PI * 0.55f * side;
            this.shortElf.addChild(ear);
        }

        this.longElf = new ModelRenderer(this.base);
        addChild(this.longElf);
        for (int i = 0; i < 2; i++) {
            final boolean leftSide = i % 2 == 0;
            final int side = leftSide ? -1 : 1;
            final ModelRenderer ear = new ModelRenderer(this.base, 24, 0);
            ear.mirror = leftSide;
            ear.addBox(-1.5f, 0, -0.5f, 3, 7, 1);
            ear.setRotationPoint(-4f * side, -4f, 0);
            ear.rotateAngleX = (float) Math.PI * 0.3f;
            ear.rotateAngleY = (float) Math.PI * -0.3f * side;
            ear.rotateAngleZ = (float) Math.PI * 0.65f * side;
            this.longElf.addChild(ear);
        }
    }

    public void initData(ModelData data) {
        ModelPartData config = data.getPartData("ears");
        if (config == null) {
            this.isHidden = true;
            return;
        }
        this.isHidden = false;
        this.color = config.color;

        this.ears.isHidden = (config.type != 0);
        this.bunny.isHidden = (config.type != 1);
        this.shortElf.isHidden = (config.type != 2);
        this.longElf.isHidden = (config.type != 3);

        if (!config.playerTexture) {
            this.location = config.getResource();
        } else {
            this.location = null;
        }
    }
}
