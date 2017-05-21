package noppes.mpm.client.model.part.horns;

import net.minecraft.client.model.ModelRenderer;
import noppes.mpm.client.model.ModelMPM;

public class ModelBullHorns
  extends ModelRenderer
{
  public ModelBullHorns(ModelMPM base)
  {
    super(base);
    ModelRenderer Left1 = new ModelRenderer(base, 36, 16);
    Left1.mirror = true;
    Left1.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2);
    Left1.setRotationPoint(4.0F, -8.0F, -2.0F);
    addChild(Left1);
    
    ModelRenderer Right1 = new ModelRenderer(base, 36, 16);
    Right1.addBox(-3.0F, 0.0F, 0.0F, 2, 2, 2);
    Right1.setRotationPoint(-3.0F, -8.0F, -2.0F);
    addChild(Right1);
    
    ModelRenderer Left2 = new ModelRenderer(base, 12, 16);
    Left2.mirror = true;
    Left2.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2);
    Left2.setRotationPoint(5.0F, -8.0F, -2.0F);
    setRotation(Left2, 0.0371786F, 0.3346075F, -0.2602503F);
    addChild(Left2);
    
    ModelRenderer Right2 = new ModelRenderer(base, 12, 16);
    Right2.addBox(-2.0F, 0.0F, 0.0F, 2, 2, 2);
    Right2.setRotationPoint(-5.0F, -8.0F, -2.0F);
    setRotation(Right2, 0.0371786F, -0.3346075F, 0.2602503F);
    addChild(Right2);
    
    ModelRenderer Left3 = new ModelRenderer(base, 13, 17);
    Left3.mirror = true;
    Left3.addBox(-1.0F, 0.0F, 0.0F, 2, 1, 1);
    Left3.setRotationPoint(7.0F, -8.0F, -2.0F);
    setRotation(Left3, 0.2602503F, 0.8551081F, -0.4089647F);
    addChild(Left3);
    
    ModelRenderer Right3 = new ModelRenderer(base, 13, 17);
    Right3.addBox(-1.0F, 0.0F, 0.0F, 2, 1, 1);
    Right3.setRotationPoint(-7.0F, -8.0F, -2.0F);
    setRotation(Right3, -0.2602503F, -0.8551081F, 0.4089647F);
    addChild(Right3);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
}
