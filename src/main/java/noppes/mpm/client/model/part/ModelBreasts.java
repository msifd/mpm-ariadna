package noppes.mpm.client.model.part;

import net.minecraft.client.model.ModelRenderer;
import noppes.mpm.client.model.Model2DRenderer;
import noppes.mpm.client.model.ModelMPM;
import noppes.mpm.data.ModelData;

public class ModelBreasts extends noppes.mpm.client.model.ModelPartInterface {
    private final Model2DRenderer plainBreasts;
    private final ModelRenderer breasts2;
    private final ModelRenderer breasts3;

    public ModelBreasts(ModelMPM base) {
        super(base);

        plainBreasts = new Model2DRenderer(base, 20.0F, 22.0F, 8, 3);
        plainBreasts.setRotationPoint(-3.6F, 5.2F, -3.0F);
        plainBreasts.setScale(0.17F, 0.19F);
        plainBreasts.setThickness(1.0F);
        plainBreasts.setPrecision(2);
        addChild(plainBreasts);

        // // // //

        this.breasts2 = new ModelRenderer(base);
        addChild(this.breasts2);

        Model2DRenderer bottom = new Model2DRenderer(base, 20.0F, 22.0F, 8, 4);
        bottom.setRotationPoint(-3.6F, 5.0F, -3.1F);
        bottom.setScale(0.225F, 0.2F);
        bottom.setThickness(2.0F);
        bottom.setPrecision(2);
        bottom.rotateAngleX = -0.31415927F;
        this.breasts2.addChild(bottom);

        this.breasts3 = new ModelRenderer(base);
        addChild(this.breasts3);

        Model2DRenderer right = new Model2DRenderer(base, 20.0F, 22.0F, 3, 2);
        right.setRotationPoint(-3.8F, 5.3F, -3.6F);
        right.setScale(0.12F, 0.14F);
        right.setThickness(1.75F);
        right.setPrecision(2);
        this.breasts3.addChild(right);

        Model2DRenderer right2 = new Model2DRenderer(base, 20.0F, 22.0F, 3, 1);
        right2.setRotationPoint(-3.8F, 4.1F, -3.14F);
        right2.setScale(0.06F, 0.07F);
        right2.setThickness(1.75F);
        right2.setPrecision(2);
        right2.rotateAngleX = 0.34906584F;
        this.breasts3.addChild(right2);

        Model2DRenderer right3 = new Model2DRenderer(base, 20.0F, 24.0F, 3, 1);
        right3.setRotationPoint(-3.8F, 5.3F, -3.6F);
        right3.setScale(0.06F, 0.07F);
        right3.setThickness(1.75F);
        right3.setPrecision(2);
        right3.rotateAngleX = -0.34906584F;
        this.breasts3.addChild(right3);

        Model2DRenderer right4 = new Model2DRenderer(base, 23.0F, 22.0F, 1, 2);
        right4.setRotationPoint(-1.8F, 5.3F, -3.14F);
        right4.setScale(0.12F, 0.14F);
        right4.setThickness(1.75F);
        right4.setPrecision(2);
        right4.rotateAngleY = 0.34906584F;
        this.breasts3.addChild(right4);

        Model2DRenderer left = new Model2DRenderer(base, 25.0F, 22.0F, 3, 2);
        left.setRotationPoint(0.8F, 5.3F, -3.6F);
        left.setScale(0.12F, 0.14F);
        left.setThickness(1.75F);
        left.setPrecision(2);
        this.breasts3.addChild(left);

        Model2DRenderer left2 = new Model2DRenderer(base, 25.0F, 22.0F, 3, 1);
        left2.setRotationPoint(0.8F, 4.1F, -3.18F);
        left2.setScale(0.06F, 0.07F);
        left2.setThickness(1.75F);
        left2.setPrecision(2);
        left2.rotateAngleX = 0.34906584F;
        this.breasts3.addChild(left2);

        Model2DRenderer left3 = new Model2DRenderer(base, 25.0F, 24.0F, 3, 1);
        left3.setRotationPoint(0.8F, 5.3F, -3.6F);
        left3.setScale(0.06F, 0.07F);
        left3.setThickness(1.75F);
        left3.setPrecision(2);
        left3.rotateAngleX = -0.34906584F;
        this.breasts3.addChild(left3);

        Model2DRenderer left4 = new Model2DRenderer(base, 24.0F, 22.0F, 1, 2);
        left4.setRotationPoint(0.8F, 5.3F, -3.6F);
        left4.setScale(0.12F, 0.14F);
        left4.setThickness(1.75F);
        left4.setPrecision(2);
        left4.rotateAngleY = -0.34906584F;
        this.breasts3.addChild(left4);
    }

    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, net.minecraft.entity.Entity entity) {
    }

    public void initData(ModelData data) {
        this.isHidden = (data.breasts == 0);
        this.plainBreasts.isHidden = (data.breasts != 1);
        this.breasts2.isHidden = (data.breasts != 2);
        this.breasts3.isHidden = (data.breasts != 3);
    }
}
