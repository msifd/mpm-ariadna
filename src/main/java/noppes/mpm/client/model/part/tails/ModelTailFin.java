package noppes.mpm.client.model.part.tails;

import net.minecraft.client.model.ModelRenderer;
import noppes.mpm.client.model.ModelMPM;

public class ModelTailFin extends ModelRenderer {
    public ModelTailFin(ModelMPM base) {
        super(base);
        ModelRenderer Shape1 = new ModelRenderer(base, 0, 0);
        Shape1.addBox(-2.0F, -2.0F, -2.0F, 3, 3, 8);
        Shape1.setRotationPoint(0.5F, 0.0F, 1.0F);
        setRotation(Shape1, -0.669215F, 0.0F, 0.0F);
        addChild(Shape1);

        ModelRenderer Shape2 = new ModelRenderer(base, 2, 2);
        Shape2.addBox(-1.0F, -1.0F, 1.0F, 3, 2, 6);
        Shape2.setRotationPoint(-0.5F, 3.0F, 4.5F);
        setRotation(Shape2, -0.2602503F, 0.0F, 0.0F);
        addChild(Shape2);

        ModelRenderer Shape3 = new ModelRenderer(base, 0, 11);
        Shape3.addBox(-1.0F, -1.0F, -1.0F, 3, 1, 6);
        Shape3.setRotationPoint(0.5F, 5.0F, 12.0F);
        setRotation(Shape3, 0.0F, 1.07818F, 0.0F);
        addChild(Shape3);

        ModelRenderer Shape4 = new ModelRenderer(base, 0, 11);
        Shape4.mirror = true;
        Shape4.addBox(-2.0F, 0.0F, -1.0F, 3, 1, 6);
        Shape4.setRotationPoint(-0.5F, 4.0F, 12.0F);
        setRotation(Shape4, 0.0F, -1.003822F, 0.0F);
        addChild(Shape4);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
