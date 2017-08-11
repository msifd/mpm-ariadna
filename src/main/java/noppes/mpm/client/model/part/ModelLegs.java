package noppes.mpm.client.model.part;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import noppes.mpm.ModelData;
import noppes.mpm.ModelPartConfig;
import noppes.mpm.ModelPartData;
import noppes.mpm.client.ClientProxy;
import noppes.mpm.client.model.ModelMPM;
import noppes.mpm.client.model.ModelScaleRenderer;
import noppes.mpm.client.model.part.legs.ModelDigitigradeLegs;
import noppes.mpm.client.model.part.legs.ModelMermaidLegs;
import noppes.mpm.client.model.part.legs.ModelNagaLegs;
import noppes.mpm.constants.EnumAnimation;
import org.lwjgl.opengl.GL11;




public class ModelLegs
  extends ModelScaleRenderer
{
  private ModelData data;
  private EntityLivingBase entity;
  private ModelScaleRenderer leg1;
  private ModelScaleRenderer leg2;
  private ModelRenderer spider;
  private ModelRenderer horse;
  private ModelNagaLegs naga;
  private ModelDigitigradeLegs digitigrade;
  private ModelMermaidLegs mermaid;
  private ModelRenderer spiderLeg1;
  private ModelRenderer spiderLeg2;
  private ModelRenderer spiderLeg3;
  private ModelRenderer spiderLeg4;
  private ModelRenderer spiderLeg5;
  private ModelRenderer spiderLeg6;
  private ModelRenderer spiderLeg7;
  private ModelRenderer spiderLeg8;
  private ModelRenderer spiderBody;
  private ModelRenderer spiderNeck;
  private ModelRenderer backLeftLeg;
  private ModelRenderer backLeftShin;
  private ModelRenderer backLeftHoof;
  private ModelRenderer backRightLeg;
  private ModelRenderer backRightShin;
  private ModelRenderer backRightHoof;
  private ModelRenderer frontLeftLeg;
  private ModelRenderer frontLeftShin;
  private ModelRenderer frontLeftHoof;
  private ModelRenderer frontRightLeg;
  private ModelRenderer frontRightShin;
  private ModelRenderer frontRightHoof;
  private ModelMPM base;
  
  public ModelLegs(ModelMPM base, ModelScaleRenderer leg1, ModelScaleRenderer leg2) {
    super(base);
    this.base = base;
    this.leg1 = leg1;
    this.leg2 = leg2;
    
    if (base.isArmor)
      return;

    this.naga = new ModelNagaLegs(base);
    addChild(this.naga);

    this.digitigrade = new ModelDigitigradeLegs(base);
    addChild(this.digitigrade);

    int baseHeight = base.textureHeight;
    base.textureHeight = 32;

    this.spider = new ModelRenderer(base);
    addChild(this.spider);
    
    float var1 = 0.0F;
    byte var2 = 15;
    this.spiderNeck = new ModelRenderer(base, 0, 0);
    this.spiderNeck.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, var1);
    this.spiderNeck.setRotationPoint(0.0F, var2, 2.0F);
    this.spider.addChild(this.spiderNeck);
    
    this.spiderBody = new ModelRenderer(base, 0, 12);
    this.spiderBody.addBox(-5.0F, -4.0F, -6.0F, 10, 8, 12, var1);
    this.spiderBody.setRotationPoint(0.0F, var2, 11.0F);
    this.spider.addChild(this.spiderBody);
    
    this.spiderLeg1 = new ModelRenderer(base, 18, 0);
    this.spiderLeg1.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, var1);
    this.spiderLeg1.setRotationPoint(-4.0F, var2, 4.0F);
    this.spider.addChild(this.spiderLeg1);
    
    this.spiderLeg2 = new ModelRenderer(base, 18, 0);
    this.spiderLeg2.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, var1);
    this.spiderLeg2.setRotationPoint(4.0F, var2, 4.0F);
    this.spider.addChild(this.spiderLeg2);
    
    this.spiderLeg3 = new ModelRenderer(base, 18, 0);
    this.spiderLeg3.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, var1);
    this.spiderLeg3.setRotationPoint(-4.0F, var2, 3.0F);
    this.spider.addChild(this.spiderLeg3);
    
    this.spiderLeg4 = new ModelRenderer(base, 18, 0);
    this.spiderLeg4.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, var1);
    this.spiderLeg4.setRotationPoint(4.0F, var2, 3.0F);
    this.spider.addChild(this.spiderLeg4);
    
    this.spiderLeg5 = new ModelRenderer(base, 18, 0);
    this.spiderLeg5.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, var1);
    this.spiderLeg5.setRotationPoint(-4.0F, var2, 2.0F);
    this.spider.addChild(this.spiderLeg5);
    
    this.spiderLeg6 = new ModelRenderer(base, 18, 0);
    this.spiderLeg6.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, var1);
    this.spiderLeg6.setRotationPoint(4.0F, var2, 2.0F);
    this.spider.addChild(this.spiderLeg6);
    
    this.spiderLeg7 = new ModelRenderer(base, 18, 0);
    this.spiderLeg7.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, var1);
    this.spiderLeg7.setRotationPoint(-4.0F, var2, 1.0F);
    this.spider.addChild(this.spiderLeg7);
    
    this.spiderLeg8 = new ModelRenderer(base, 18, 0);
    this.spiderLeg8.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, var1);
    this.spiderLeg8.setRotationPoint(4.0F, var2, 1.0F);
    this.spider.addChild(this.spiderLeg8);
    

    int zOffset = 10;
    float yOffset = 7.0F;
    this.horse = new ModelRenderer(base);
    addChild(this.horse);
    
    ModelRenderer body = new ModelRenderer(base, 0, 34);
    body.setTextureSize(128, 128);
    body.addBox(-5.0F, -8.0F, -19.0F, 10, 10, 24);
    body.setRotationPoint(0.0F, 11.0F + yOffset, 9.0F + zOffset);
    this.horse.addChild(body);
    
    this.backLeftLeg = new ModelRenderer(base, 78, 29);
    this.backLeftLeg.setTextureSize(128, 128);
    this.backLeftLeg.addBox(-2.5F, -2.0F, -2.5F, 4, 9, 5);
    this.backLeftLeg.setRotationPoint(4.0F, 9.0F + yOffset, 11.0F + zOffset);
    this.horse.addChild(this.backLeftLeg);
    this.backLeftShin = new ModelRenderer(base, 78, 43);
    this.backLeftShin.setTextureSize(128, 128);
    this.backLeftShin.addBox(-2.0F, 0.0F, -1.5F, 3, 5, 3);
    this.backLeftShin.setRotationPoint(0.0F, 7.0F, 0.0F);
    this.backLeftLeg.addChild(this.backLeftShin);
    this.backLeftHoof = new ModelRenderer(base, 78, 51);
    this.backLeftHoof.setTextureSize(128, 128);
    this.backLeftHoof.addBox(-2.5F, 5.1F, -2.0F, 4, 3, 4);
    this.backLeftHoof.setRotationPoint(0.0F, 7.0F, 0.0F);
    this.backLeftLeg.addChild(this.backLeftHoof);
    
    this.backRightLeg = new ModelRenderer(base, 96, 29);
    this.backRightLeg.setTextureSize(128, 128);
    this.backRightLeg.addBox(-1.5F, -2.0F, -2.5F, 4, 9, 5);
    this.backRightLeg.setRotationPoint(-4.0F, 9.0F + yOffset, 11.0F + zOffset);
    this.horse.addChild(this.backRightLeg);
    this.backRightShin = new ModelRenderer(base, 96, 43);
    this.backRightShin.setTextureSize(128, 128);
    this.backRightShin.addBox(-1.0F, 0.0F, -1.5F, 3, 5, 3);
    this.backRightShin.setRotationPoint(0.0F, 7.0F, 0.0F);
    this.backRightLeg.addChild(this.backRightShin);
    this.backRightHoof = new ModelRenderer(base, 96, 51);
    this.backRightHoof.setTextureSize(128, 128);
    this.backRightHoof.addBox(-1.5F, 5.1F, -2.0F, 4, 3, 4);
    this.backRightHoof.setRotationPoint(0.0F, 7.0F, 0.0F);
    this.backRightLeg.addChild(this.backRightHoof);
    
    this.frontLeftLeg = new ModelRenderer(base, 44, 29);
    this.frontLeftLeg.setTextureSize(128, 128);
    this.frontLeftLeg.addBox(-1.9F, -1.0F, -2.1F, 3, 8, 4);
    this.frontLeftLeg.setRotationPoint(4.0F, 9.0F + yOffset, -8.0F + zOffset);
    this.horse.addChild(this.frontLeftLeg);
    this.frontLeftShin = new ModelRenderer(base, 44, 41);
    this.frontLeftShin.setTextureSize(128, 128);
    this.frontLeftShin.addBox(-1.9F, 0.0F, -1.6F, 3, 5, 3);
    this.frontLeftShin.setRotationPoint(0.0F, 7.0F, 0.0F);
    this.frontLeftLeg.addChild(this.frontLeftShin);
    this.frontLeftHoof = new ModelRenderer(base, 44, 51);
    this.frontLeftHoof.setTextureSize(128, 128);
    this.frontLeftHoof.addBox(-2.4F, 5.1F, -2.1F, 4, 3, 4);
    this.frontLeftHoof.setRotationPoint(0.0F, 7.0F, 0.0F);
    this.frontLeftLeg.addChild(this.frontLeftHoof);
    
    this.frontRightLeg = new ModelRenderer(base, 60, 29);
    this.frontRightLeg.setTextureSize(128, 128);
    this.frontRightLeg.addBox(-1.1F, -1.0F, -2.1F, 3, 8, 4);
    this.frontRightLeg.setRotationPoint(-4.0F, 9.0F + yOffset, -8.0F + zOffset);
    this.horse.addChild(this.frontRightLeg);
    this.frontRightShin = new ModelRenderer(base, 60, 41);
    this.frontRightShin.setTextureSize(128, 128);
    this.frontRightShin.addBox(-1.1F, 0.0F, -1.6F, 3, 5, 3);
    this.frontRightShin.setRotationPoint(0.0F, 7.0F, 0.0F);
    this.frontRightLeg.addChild(this.frontRightShin);
    this.frontRightHoof = new ModelRenderer(base, 60, 51);
    this.frontRightHoof.setTextureSize(128, 128);
    this.frontRightHoof.addBox(-1.6F, 5.1F, -2.1F, 4, 3, 4);
    this.frontRightHoof.setRotationPoint(0.0F, 7.0F, 0.0F);
    this.frontRightLeg.addChild(this.frontRightHoof);

    this.mermaid = new ModelMermaidLegs(base);
    addChild(this.mermaid);

    base.textureHeight = baseHeight;
  }
  
  public void setRotation(ModelRenderer model, float x, float y, float z) { model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {
    ModelPartData part = this.data.legParts;
    this.rotationPointZ = 0.0F;
    this.rotationPointY = 0.0F;
    
    if (this.base.isArmor)
      return;
    if (part.type == 2) {
      this.rotateAngleX = 0.0F;
      this.spiderBody.rotationPointY = 15.0F;
      this.spiderBody.rotationPointZ = 11.0F;
      this.spiderNeck.rotateAngleX = 0.0F;
      
      float var8 = 0.7853982F;
      this.spiderLeg1.rotateAngleZ = (-var8);
      this.spiderLeg2.rotateAngleZ = var8;
      this.spiderLeg3.rotateAngleZ = (-var8 * 0.74F);
      this.spiderLeg4.rotateAngleZ = (var8 * 0.74F);
      this.spiderLeg5.rotateAngleZ = (-var8 * 0.74F);
      this.spiderLeg6.rotateAngleZ = (var8 * 0.74F);
      this.spiderLeg7.rotateAngleZ = (-var8);
      this.spiderLeg8.rotateAngleZ = var8;
      float var9 = -0.0F;
      float var10 = 0.3926991F;
      this.spiderLeg1.rotateAngleY = (var10 * 2.0F + var9);
      this.spiderLeg2.rotateAngleY = (-var10 * 2.0F - var9);
      this.spiderLeg3.rotateAngleY = (var10 * 1.0F + var9);
      this.spiderLeg4.rotateAngleY = (-var10 * 1.0F - var9);
      this.spiderLeg5.rotateAngleY = (-var10 * 1.0F + var9);
      this.spiderLeg6.rotateAngleY = (var10 * 1.0F - var9);
      this.spiderLeg7.rotateAngleY = (-var10 * 2.0F + var9);
      this.spiderLeg8.rotateAngleY = (var10 * 2.0F - var9);
      float var11 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + 0.0F) * 0.4F) * par2;
      float var12 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + 3.1415927F) * 0.4F) * par2;
      float var13 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + 1.5707964F) * 0.4F) * par2;
      float var14 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + 4.712389F) * 0.4F) * par2;
      float var15 = Math.abs(MathHelper.sin(par1 * 0.6662F + 0.0F) * 0.4F) * par2;
      float var16 = Math.abs(MathHelper.sin(par1 * 0.6662F + 3.1415927F) * 0.4F) * par2;
      float var17 = Math.abs(MathHelper.sin(par1 * 0.6662F + 1.5707964F) * 0.4F) * par2;
      float var18 = Math.abs(MathHelper.sin(par1 * 0.6662F + 4.712389F) * 0.4F) * par2;
      this.spiderLeg1.rotateAngleY += var11;
      this.spiderLeg2.rotateAngleY += -var11;
      this.spiderLeg3.rotateAngleY += var12;
      this.spiderLeg4.rotateAngleY += -var12;
      this.spiderLeg5.rotateAngleY += var13;
      this.spiderLeg6.rotateAngleY += -var13;
      this.spiderLeg7.rotateAngleY += var14;
      this.spiderLeg8.rotateAngleY += -var14;
      this.spiderLeg1.rotateAngleZ += var15;
      this.spiderLeg2.rotateAngleZ += -var15;
      this.spiderLeg3.rotateAngleZ += var16;
      this.spiderLeg4.rotateAngleZ += -var16;
      this.spiderLeg5.rotateAngleZ += var17;
      this.spiderLeg6.rotateAngleZ += -var17;
      this.spiderLeg7.rotateAngleZ += var18;
      this.spiderLeg8.rotateAngleZ += -var18;
      

      if (this.base.isSneak) {
        this.rotationPointZ = 5.0F;
        this.rotationPointY = -1.0F;
        this.spiderBody.rotationPointY = 16.0F;
        this.spiderBody.rotationPointZ = 10.0F;
        this.spiderNeck.rotateAngleX = -0.3926991F;
      }
      if ((this.base.isSleeping(entity)) || (this.data.animation == EnumAnimation.CRAWLING)) {
        this.rotationPointY = (12.0F * this.data.legs.scaleY);
        this.rotationPointZ = (15.0F * this.data.legs.scaleY);
        
        this.rotateAngleX = -1.5707964F;
      }
    }
    else if (part.type == 3) {
      this.frontLeftLeg.rotateAngleX = (MathHelper.cos(par1 * 0.6662F) * 0.4F * par2);
      this.frontRightLeg.rotateAngleX = (MathHelper.cos(par1 * 0.6662F + 3.1415927F) * 0.4F * par2);
      this.backLeftLeg.rotateAngleX = (MathHelper.cos(par1 * 0.6662F + 3.1415927F) * 0.4F * par2);
      this.backRightLeg.rotateAngleX = (MathHelper.cos(par1 * 0.6662F) * 0.4F * par2);
    }
    else if (part.type == 1) {
      this.naga.isRiding = this.base.isRiding;
      this.naga.isSleeping = this.base.isSleeping(entity);
      this.naga.isCrawling = (this.data.animation == EnumAnimation.CRAWLING);
      this.naga.isSneaking = this.base.isSneak;
      this.naga.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
    }
    else if (part.type == 4) {
      this.mermaid.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
    }
    else if (part.type == 5) {
      this.digitigrade.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
    }
  }
  

  public void render(float par1)
  {
    if ((!this.showModel) || (this.isHidden))
      return;
    ModelPartData part = this.data.legParts;
    if (part.type < 0)
      return;
    GL11.glPushMatrix();
    if (part.type == 4)
      part.playerTexture = (!this.entity.isInWater());
    if (!this.base.isArmor) {
      if (!part.playerTexture) {
        ClientProxy.bindTexture(part.getResource());
        this.base.currentlyPlayerTexture = false;
      }
      else if (!this.base.currentlyPlayerTexture) {
        ClientProxy.bindTexture(this.data.playerResource);
        this.base.currentlyPlayerTexture = true;
      }
    }
    if ((part.type == 0) || ((part.type == 4) && (!this.entity.isInWater()))) {
      this.leg1.setConfig(this.config, this.x, this.y, this.z);
      this.leg1.render(par1);
      this.leg2.setConfig(this.config, -this.x, this.y, this.z);
      this.leg2.render(par1);
    }
    
    if (!this.base.isArmor) {
      this.naga.isHidden = (part.type != 1);
      this.spider.isHidden = (part.type != 2);
      this.horse.isHidden = (part.type != 3);
      this.mermaid.isHidden = ((part.type != 4) || (!this.entity.isInWater()));
      this.digitigrade.isHidden = (part.type != 5);
      
      if (!this.horse.isHidden) {
        this.x = 0.0F;
        this.y *= 1.8F;
        GL11.glScalef(0.9F, 0.9F, 0.9F);
      }
      else if (!this.spider.isHidden) {
        this.x = 0.0F;
        this.y *= 2.0F;
      }
      else if (!this.naga.isHidden) {
        this.x = 0.0F;
        this.y *= 2.0F;
      }
      else if ((!this.mermaid.isHidden) || (!this.digitigrade.isHidden)) {
        this.x = 0.0F;
        this.y *= 2.0F;
      }
    }
    boolean bo = (this.entity.hurtTime <= 0) && (this.entity.deathTime <= 0) && (!this.base.isArmor);
    if (bo) {
      float red = (this.data.legParts.color >> 16 & 0xFF) / 255.0F;
      float green = (this.data.legParts.color >> 8 & 0xFF) / 255.0F;
      float blue = (this.data.legParts.color & 0xFF) / 255.0F;
      GL11.glColor3f(red, green, blue);
    }
    super.render(par1);
    if (bo) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
    GL11.glPopMatrix();
  }
  
  public void setData(ModelData data, EntityLivingBase entity) {
    this.data = data;
    this.entity = entity;
  }
}
